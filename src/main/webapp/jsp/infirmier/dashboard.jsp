<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Infirmier - Télé-Expertise Médicale</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f7fa;
            line-height: 1.6;
        }

        .header {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .header h1 {
            font-size: 24px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .logout-btn {
            background: rgba(255,255,255,0.2);
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .logout-btn:hover {
            background: rgba(255,255,255,0.3);
            color: white;
            text-decoration: none;
        }

        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }

        .welcome-card {
            background: white;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
            text-align: center;
        }

        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 2rem;
        }

        .card {
            background: white;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
            cursor: pointer;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card-icon {
            font-size: 40px;
            margin-bottom: 1rem;
        }

        .card h3 {
            color: #333;
            margin-bottom: 1rem;
            font-size: 20px;
        }

        .card p {
            color: #666;
            margin-bottom: 1.5rem;
        }

        .card-btn {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s ease;
        }

        .card-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(231, 76, 60, 0.3);
            color: white;
            text-decoration: none;
        }

        .stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
            margin-bottom: 2rem;
        }

        .stat-card {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            text-align: center;
        }

        .stat-number {
            font-size: 32px;
            font-weight: bold;
            color: #e74c3c;
            margin-bottom: 0.5rem;
        }

        .stat-label {
            color: #666;
            font-size: 14px;
        }

        .urgent-queue {
            background: linear-gradient(135deg, #f39c12 0%, #e67e22 100%);
            color: white;
            padding: 1rem;
            border-radius: 10px;
            margin-bottom: 2rem;
            text-align: center;
        }

        .urgent-queue h3 {
            margin-bottom: 0.5rem;
        }
    </style>
</head>
<body>
    <header class="header">
        <h1>
            <span>👩‍⚕️</span>
            Dashboard Infirmier
        </h1>
        <div class="user-info">
            <span>Bienvenue, ${sessionScope.userName}</span>
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Déconnexion</a>
        </div>
    </header>

    <div class="container">
        <div class="welcome-card">
            <h2>Bienvenue dans votre espace Infirmier</h2>
            <p>Gérez l'accueil des patients, enregistrez les signes vitaux et organisez la file d'attente.</p>
        </div>

        <div class="urgent-queue">
            <h3>🚨 File d'attente prioritaire</h3>
            <p>7 patients en attente de consultation</p>
        </div>

        <div class="stats">
            <div class="stat-card">
                <div class="stat-number">23</div>
                <div class="stat-label">Patients enregistrés aujourd'hui</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">7</div>
                <div class="stat-label">En file d'attente</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">16</div>
                <div class="stat-label">Consultations terminées</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">12min</div>
                <div class="stat-label">Temps d'attente moyen</div>
            </div>
        </div>

        <div class="dashboard-grid">
            <div class="card">
                <div class="card-icon">👤</div>
                <h3>Accueil Patient</h3>
                <p>Enregistrer un nouveau patient ou rechercher un patient existant</p>
                <a href="#" class="card-btn">Commencer</a>
            </div>

            <div class="card">
                <div class="card-icon">❤️</div>
                <h3>Signes Vitaux</h3>
                <p>Mesurer et enregistrer les signes vitaux des patients</p>
                <a href="#" class="card-btn">Enregistrer</a>
            </div>

            <div class="card">
                <div class="card-icon">📋</div>
                <h3>Liste des Patients</h3>
                <p>Voir la liste des patients enregistrés aujourd'hui</p>
                <a href="#" class="card-btn">Voir la liste</a>
            </div>

            <div class="card">
                <div class="card-icon">⏰</div>
                <h3>File d'Attente</h3>
                <p>Gérer la file d'attente des patients</p>
                <a href="#" class="card-btn">Gérer</a>
            </div>

            <div class="card">
                <div class="card-icon">📊</div>
                <h3>Statistiques du Jour</h3>
                <p>Voir les statistiques d'accueil du jour</p>
                <a href="#" class="card-btn">Analyser</a>
            </div>

            <div class="card">
                <div class="card-icon">⚙️</div>
                <h3>Mon Profil</h3>
                <p>Modifier mes informations personnelles</p>
                <a href="#" class="card-btn">Modifier</a>
            </div>
        </div>
    </div>

    <script>
        document.querySelectorAll('.card').forEach(card => {
            card.addEventListener('click', function(e) {
                if (!e.target.classList.contains('card-btn')) {
                    const btn = this.querySelector('.card-btn');
                    if (btn) btn.click();
                }
            });
        });

        // Auto-refresh queue status every 30 seconds
        setInterval(() => {
            // This would be replaced with actual AJAX call
            console.log('Refreshing queue status...');
        }, 30000);
    </script>
</body>
</html>
