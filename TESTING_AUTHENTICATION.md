# Testing the Authentication System

## 📋 Prerequisites

1. PostgreSQL database running
2. Database `medex_test` created
3. Tomcat or other servlet container configured

## 🚀 Step-by-Step Testing Guide

### Step 1: Generate BCrypt Password Hashes

Run the password generator to get the correct BCrypt hash:

```bash
cd src/main/java
javac -cp "../../../target/classes:../../../target/JEE-medex-teleexpertise-1.0-SNAPSHOT/WEB-INF/lib/*" com/example/jeemedexteleexpertise/util/PasswordHashGenerator.java
java -cp "../../../target/classes:../../../target/JEE-medex-teleexpertise-1.0-SNAPSHOT/WEB-INF/lib/*:." com.example.jeemedexteleexpertise.util.PasswordHashGenerator
```

**Or simply run from IntelliJ:** Right-click `PasswordHashGenerator.java` → Run

This will output the BCrypt hash and SQL insert statements.

### Step 2: Initialize Database with Test Users

**Option A - Let Hibernate create tables first:**
1. Build and deploy the application (Hibernate will create tables)
2. Then run the SQL script

**Option B - Manual table creation:**
1. Start the application once to let Hibernate create tables
2. Or create the utilisateur table manually

**Run the SQL script:**
```bash
# Copy the SQL output from PasswordHashGenerator
# Then run in psql:
psql -U postgres -d medex_test

# Paste the INSERT statements from PasswordHashGenerator output
```

**Quick Manual Insert (after getting hash from generator):**
```sql
INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role, actif, date_creation) VALUES
('Alami', 'Hassan', 'infirmier@medex.com', 'PASTE_BCRYPT_HASH_HERE', 'INFIRMIER', true, CURRENT_TIMESTAMP),
('Bennani', 'Fatima', 'generaliste@medex.com', 'PASTE_BCRYPT_HASH_HERE', 'GENERALISTE', true, CURRENT_TIMESTAMP),
('Tazi', 'Mohammed', 'specialiste@medex.com', 'PASTE_BCRYPT_HASH_HERE', 'SPECIALISTE', true, CURRENT_TIMESTAMP);

-- Verify
SELECT id, nom, prenom, email, role, actif FROM utilisateur;
```

### Step 3: Build and Deploy

```bash
# Build the WAR file
.\mvnw.cmd clean package

# Deploy to Tomcat
# Copy target/JEE-medex-teleexpertise-1.0-SNAPSHOT.war to Tomcat webapps/
```

### Step 4: Test Authentication

**1. Access the login page:**
```
http://localhost:8080/JEE-medex-teleexpertise-1.0-SNAPSHOT/login
```

**2. Test with Infirmier account:**
- Email: `infirmier@medex.com`
- Password: `password`
- Expected: Redirects to `/infirmier/dashboard`
- Should see: "Bienvenue sur le système de Télé-Expertise Médicale" with INFIRMIER badge

**3. Test with Généraliste account:**
- Email: `generaliste@medex.com`
- Password: `password`
- Expected: Redirects to `/generaliste/dashboard`
- Should see: Green-themed dashboard with GÉNÉRALISTE badge

**4. Test with Spécialiste account:**
- Email: `specialiste@medex.com`
- Password: `password`
- Expected: Redirects to `/specialiste/dashboard`
- Should see: Orange-themed dashboard with SPÉCIALISTE badge

### Step 5: Test Security Features

**Test 1: Invalid Credentials**
- Try logging in with wrong password
- Expected: Error message "Email ou mot de passe incorrect"

**Test 2: Role-Based Access Control**
- Login as infirmier
- Try to access: `http://localhost:8080/JEE-medex-teleexpertise-1.0-SNAPSHOT/generaliste/dashboard`
- Expected: Redirected to login page (AuthenticationFilter blocks it)

**Test 3: Session Management**
- Login successfully
- Close browser
- Open browser and try to access dashboard directly
- Expected: Session should persist (depending on browser settings)

**Test 4: Logout**
- Click "Déconnexion" button
- Expected: Redirected to login page
- Try accessing dashboard again
- Expected: Redirected to login page (session invalidated)

**Test 5: Already Logged In**
- Login successfully
- Try to access `/login` again
- Expected: Automatically redirected to your dashboard

## 🔍 Troubleshooting

### Issue: "Email ou mot de passe incorrect" even with correct credentials

**Solution:**
1. Verify BCrypt hash is correct:
   ```bash
   # Run PasswordHashGenerator again to get fresh hash
   ```

2. Check database:
   ```sql
   SELECT email, mot_de_passe, role, actif FROM utilisateur WHERE email = 'infirmier@medex.com';
   ```

3. Verify the hash starts with `$2a$10$`

### Issue: Page not found

**Solution:**
1. Check Tomcat logs: `catalina.out` or `localhost.log`
2. Verify WAR is deployed: Check `webapps/` folder
3. Check servlet mapping in logs

### Issue: Database connection error

**Solution:**
1. Verify `persistence.xml` settings:
   ```xml
   <property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/medex_test"/>
   <property name="jakarta.persistence.jdbc.user" value="postgres"/>
   <property name="jakarta.persistence.jdbc.password" value="root"/>
   ```

2. Test PostgreSQL connection:
   ```bash
   psql -U postgres -d medex_test -c "SELECT 1;"
   ```

### Issue: Session not persisting

**Solution:**
Check `persistence.xml`:
```xml
<property name="hibernate.hbm2ddl.auto" value="update"/>
<!-- NOT "create-drop" in production! -->
```

## ✅ Test Checklist

- [ ] PasswordHashGenerator runs and produces BCrypt hash
- [ ] Database has utilisateur table
- [ ] Test users inserted successfully
- [ ] Application builds without errors
- [ ] WAR deploys to Tomcat
- [ ] Login page loads at `/login`
- [ ] Can login as infirmier → redirects to infirmier dashboard
- [ ] Can login as generaliste → redirects to generaliste dashboard
- [ ] Can login as specialiste → redirects to specialiste dashboard
- [ ] Invalid credentials show error
- [ ] Cannot access other role's dashboard
- [ ] Logout works correctly
- [ ] Session invalidated after logout
- [ ] Already logged in redirects to dashboard

## 📊 Expected Results

**Login Success Flow:**
```
POST /login (email + password)
  ↓
UtilisateurService.authenticate()
  ↓
BCrypt.checkpw(password, hashedPassword)
  ↓
Create HttpSession with user attributes
  ↓
Redirect to role-specific dashboard
```

**Protected Page Access:**
```
GET /infirmier/dashboard
  ↓
AuthenticationFilter.doFilter()
  ↓
Check session.getAttribute("user")
  ↓
Check user.getRole() == INFIRMIER
  ↓
Allow access to InfirmierDashboardServlet
```

## 📁 Created Files Structure

```
controller/
├── LoginServlet.java           (/login)
├── LogoutServlet.java          (/logout)
├── infirmier/
│   └── InfirmierDashboardServlet.java    (/infirmier/dashboard)
├── generaliste/
│   └── GeneralisteDashboardServlet.java  (/generaliste/dashboard)
└── specialiste/
    └── SpecialisteDashboardServlet.java  (/specialiste/dashboard)

filter/
└── AuthenticationFilter.java   (Protects /infirmier/*, /generaliste/*, /specialiste/*)

util/
└── PasswordHashGenerator.java  (Generate BCrypt hashes)

resources/
└── test-users.sql             (SQL insert script)
```

## 🎯 Next Steps After Successful Testing

Once authentication works:
1. ✅ Authentication system complete
2. 🔄 Next: Implement Module Infirmier (US1, US2)
   - Patient search and registration
   - Queue management
3. Then: Module Généraliste
4. Then: Module Spécialiste

---

**For questions or issues, check:**
- Tomcat logs: `logs/catalina.out`
- Browser console (F12)
- Database logs

