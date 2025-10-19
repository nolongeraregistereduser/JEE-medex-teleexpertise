<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - Système Télé-Expertise Médicale</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .login-container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .login-header h1 {
            color: #333;
            margin-bottom: 10px;
            font-size: 28px;
        }

        .login-header p {
            color: #666;
            font-size: 14px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #333;
            font-weight: 500;
        }

        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s;
        }

        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }

        .error-message {
            background-color: #fee;
            color: #c33;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            border-left: 4px solid #c33;
        }

        .btn-login {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: transform 0.2s;
        }

        .btn-login:hover {
            transform: translateY(-2px);
        }

        .btn-login:active {
            transform: translateY(0);
        }

        .info-box {
            margin-top: 30px;
            padding: 15px;
            background-color: #f0f4ff;
            border-radius: 8px;
            font-size: 12px;
            color: #555;
        }

        .info-box h3 {
            color: #667eea;
            margin-bottom: 10px;
            font-size: 14px;
        }

        .info-box ul {
            list-style: none;
            padding-left: 0;
        }

        .info-box li {
            padding: 5px 0;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1>🏥 Télé-Expertise Médicale</h1>
            <p>Connexion au système</p>
        </div>

        <c:if test="${not empty error}">
            <div class="error-message">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email"
                       id="email"
                       name="email"
                       value="${email}"
                       placeholder="votre.email@exemple.com"
                       required>
            </div>

            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input type="password"
                       id="password"
                       name="password"
                       placeholder="••••••••"
                       required>
            </div>

            <button type="submit" class="btn-login">Se connecter</button>
        </form>

        <div class="info-box">
            <h3>Comptes de test :</h3>
            <ul>
                <li>👨‍⚕️ <strong>Infirmier:</strong> infirmier@medex.com / password</li>
                <li>👨‍⚕️ <strong>Généraliste:</strong> generaliste@medex.com / password</li>
                <li>👨‍⚕️ <strong>Spécialiste:</strong> specialiste@medex.com / password</li>
            </ul>
        </div>
    </div>
</body>
</html>
