<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord Spécialiste - Medex</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #e67e22 0%, #d35400 100%);
            min-height: 100vh;
            padding: 20px;
        }

        .dashboard-container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .header {
            background: white;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header h1 {
            color: #333;
            font-size: 24px;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .user-name {
            font-weight: 500;
            color: #555;
        }

        .btn-logout {
            background: #e74c3c;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }

        .btn-logout:hover {
            background: #c0392b;
        }

        .welcome-card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .welcome-card h2 {
            color: #e67e22;
            margin-bottom: 15px;
        }

        .welcome-card p {
            color: #666;
            font-size: 16px;
        }

        .role-badge {
            display: inline-block;
            background: #e67e22;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <div class="header">
            <h1>🔬 Tableau de Bord Spécialiste</h1>
            <div class="user-info">
                <span class="user-name">👤 ${sessionScope.userName}</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Déconnexion</a>
            </div>
        </div>

        <div class="welcome-card">
            <h2>Bienvenue sur le système de Télé-Expertise Médicale</h2>
            <p>Vous êtes connecté en tant que médecin spécialiste</p>
            <span class="role-badge">SPÉCIALISTE</span>
            <p style="margin-top: 20px; color: #999;">
                Email: ${sessionScope.userEmail}
            </p>
        </div>
    </div>
</body>
</html>

