<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Sélectionner Spécialité - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .card {
            background: white;
            border-radius: 15px;
            padding: 20px;
        }
        .specialty-card {
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            padding: 20px;
            text-align: center;
            transition: all 0.3s;
            cursor: pointer;
            text-decoration: none;
            display: block;
            color: #333;
        }
        .specialty-card:hover {
            border-color: #27ae60;
            box-shadow: 0 4px 12px rgba(46, 204, 113, 0.3);
            transform: translateY(-5px);
            color: #27ae60;
        }
        .specialty-icon {
            font-size: 3rem;
            color: #27ae60;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card mb-4">
            <h2><i class="fas fa-user-md"></i> Demander un Avis Spécialiste</h2>
            <p class="text-muted">Patient: <strong>${consultation.patient.prenom} ${consultation.patient.nom}</strong></p>
        </div>

        <div class="card">
            <h4 class="mb-4">Étape 1: Sélectionnez la Spécialité</h4>

            <div class="row">
                <c:forEach items="${specialties}" var="specialty">
                    <div class="col-md-4 mb-3">
                        <a href="?consultationId=${consultation.id}&specialty=${specialty}" class="specialty-card">
                            <i class="fas fa-heartbeat specialty-icon"></i>
                            <h5>${specialty.displayName}</h5>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

