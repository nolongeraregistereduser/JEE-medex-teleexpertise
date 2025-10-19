<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord Généraliste - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
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
            margin: 0;
        }
        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        .action-card {
            background: white;
            border-radius: 15px;
            padding: 30px;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            transition: all 0.3s;
            height: 100%;
            cursor: pointer;
        }
        .action-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
        }
        .action-icon {
            font-size: 4rem;
            margin-bottom: 20px;
        }
        .card-green { border-left: 5px solid #27ae60; }
        .card-blue { border-left: 5px solid #3498db; }
        .card-orange { border-left: 5px solid #f39c12; }
        .card-purple { border-left: 5px solid #9b59b6; }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-user-md"></i> Tableau de Bord Généraliste</h1>
            <div class="user-info">
                <span><i class="fas fa-user"></i> ${sessionScope.userName}</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger btn-sm">
                    <i class="fas fa-sign-out-alt"></i> Déconnexion
                </a>
            </div>
        </div>

        <!-- Main Actions -->
        <div class="row g-4">
            <!-- Create Consultation -->
            <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/generaliste/consultation?action=new" class="text-decoration-none">
                    <div class="action-card card-green">
                        <i class="fas fa-file-medical action-icon text-success"></i>
                        <h3>Nouvelle Consultation</h3>
                        <p class="text-muted">Créer une consultation pour un patient (150 DH)</p>
                    </div>
                </a>
            </div>

            <!-- View Queue -->
            <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/generaliste/file-attente" class="text-decoration-none">
                    <div class="action-card card-blue">
                        <i class="fas fa-users action-icon text-primary"></i>
                        <h3>File d'Attente</h3>
                        <p class="text-muted">Voir les patients en attente de consultation</p>
                    </div>
                </a>
            </div>

            <!-- My Consultations -->
            <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/generaliste/consultation" class="text-decoration-none">
                    <div class="action-card card-orange">
                        <i class="fas fa-list-alt action-icon text-warning"></i>
                        <h3>Mes Consultations</h3>
                        <p class="text-muted">Voir toutes mes consultations et demandes</p>
                    </div>
                </a>
            </div>

            <!-- Request Expertise -->
            <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/generaliste/consultation" class="text-decoration-none">
                    <div class="action-card card-purple">
                        <i class="fas fa-stethoscope action-icon text-info"></i>
                        <h3>Demander Expertise</h3>
                        <p class="text-muted">Solliciter l'avis d'un spécialiste</p>
                    </div>
                </a>
            </div>
        </div>

        <!-- Quick Info -->
        <div class="mt-4 p-3 bg-white rounded shadow-sm">
            <h5><i class="fas fa-info-circle text-primary"></i> Information</h5>
            <ul class="mb-0">
                <li>Coût consultation fixe: <strong>150 DH</strong></li>
                <li>Pour demander un avis spécialiste, créez d'abord une consultation</li>
                <li>Les spécialistes sont triés par tarif croissant</li>
            </ul>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
