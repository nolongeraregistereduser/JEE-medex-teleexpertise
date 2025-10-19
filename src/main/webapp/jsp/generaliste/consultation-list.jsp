<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes Consultations - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            padding: 20px;
        }
        .container { max-width: 1400px; }
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
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 20px;
        }
        .stats-card {
            text-align: center;
            padding: 20px;
            border-left: 4px solid #27ae60;
        }
        .stats-number {
            font-size: 2.5rem;
            font-weight: bold;
            color: #27ae60;
        }
        .badge-waiting {
            background: #f39c12;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
        }
        .badge-awaiting-specialist {
            background: #3498db;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
        }
        .badge-completed {
            background: #27ae60;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
        }
        .table thead th {
            background: #27ae60;
            color: white;
            border: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-file-medical"></i> Mes Consultations</h1>
            <div>
                <a href="${pageContext.request.contextPath}/generaliste/consultation?action=new" class="btn btn-success me-2">
                    <i class="fas fa-plus"></i> Nouvelle Consultation
                </a>
                <a href="${pageContext.request.contextPath}/generaliste/dashboard" class="btn btn-primary">
                    <i class="fas fa-home"></i> Dashboard
                </a>
            </div>
        </div>

        <!-- Success Message -->
        <c:if test="${param.success == 'true'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle"></i> Consultation créée avec succès!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Statistics -->
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="card stats-card">
                    <h6>Total Consultations</h6>
                    <div class="stats-number">${totalConsultations}</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stats-card" style="border-left-color: #f39c12;">
                    <h6>En Attente</h6>
                    <div class="stats-number" style="color: #f39c12;">${enAttente}</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stats-card" style="border-left-color: #3498db;">
                    <h6>Attente Avis Spécialiste</h6>
                    <div class="stats-number" style="color: #3498db;">${enAttenteAvis}</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stats-card" style="border-left-color: #27ae60;">
                    <h6>Terminées</h6>
                    <div class="stats-number">${terminees}</div>
                </div>
            </div>
        </div>

        <!-- Consultations Table -->
        <div class="card">
            <h4 class="mb-3"><i class="fas fa-list"></i> Liste des Consultations</h4>

            <c:choose>
                <c:when test="${not empty consultations}">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Patient</th>
                                    <th>Motif</th>
                                    <th>Coût</th>
                                    <th>Statut</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${consultations}" var="consultation">
                                    <tr>

                                        <td>
                                            <i class="fas fa-calendar"></i>
                                            <fmt:formatDate value="${file.dateArrivee.toLocalTime().toString().substring(0, 5)}" pattern="dd/MM/yyyy HH:mm"/>
                                        </td>
                                        <td>
                                            <i class="fas fa-user-circle text-primary"></i>
                                            <strong>${consultation.patient.prenom} ${consultation.patient.nom}</strong>
                                        </td>
                                        <td>${consultation.motif}</td>
                                        <td>
                                            <strong>${consultation.cout} DH</strong>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${consultation.status == 'EN_ATTENTE'}">
                                                    <span class="badge-waiting">
                                                        <i class="fas fa-hourglass-half"></i> En Attente
                                                    </span>
                                                </c:when>
                                                <c:when test="${consultation.status == 'EN_ATTENTE_AVIS_SPECIALISTE'}">
                                                    <span class="badge-awaiting-specialist">
                                                        <i class="fas fa-user-md"></i> Attente Avis
                                                    </span>
                                                </c:when>
                                                <c:when test="${consultation.status == 'TERMINEE'}">
                                                    <span class="badge-completed">
                                                        <i class="fas fa-check"></i> Terminée
                                                    </span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/generaliste/consultation?action=view&id=${consultation.id}"
                                               class="btn btn-sm btn-info" title="Voir détails">
                                                <i class="fas fa-eye"></i>
                                            </a>
                                            <c:if test="${consultation.status == 'EN_ATTENTE'}">
                                                <a href="${pageContext.request.contextPath}/generaliste/demande-expertise?consultationId=${consultation.id}"
                                                   class="btn btn-sm btn-primary" title="Demander avis spécialiste">
                                                    <i class="fas fa-user-md"></i>
                                                </a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-5">
                        <i class="fas fa-inbox fa-4x text-muted mb-3"></i>
                        <h5 class="text-muted">Aucune consultation enregistrée</h5>
                        <p class="text-muted">Créez votre première consultation</p>
                        <a href="${pageContext.request.contextPath}/generaliste/consultation?action=new" class="btn btn-success mt-3">
                            <i class="fas fa-plus"></i> Nouvelle Consultation
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouvelle Consultation - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            padding: 20px;
        }
        .container { max-width: 900px; }
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
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }
        .form-label {
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 8px;
        }
        .form-control, .form-select {
            border-radius: 8px;
            border: 2px solid #e0e0e0;
            padding: 12px;
        }
        .form-control:focus, .form-select:focus {
            border-color: #27ae60;
            box-shadow: 0 0 0 0.2rem rgba(46, 204, 113, 0.25);
        }
        .btn-submit {
            background: #27ae60;
            color: white;
            padding: 12px 40px;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            width: 100%;
        }
        .btn-submit:hover {
            background: #229954;
        }
        .cost-info {
            background: #e8f5e9;
            border-left: 4px solid #27ae60;
            padding: 15px;
            border-radius: 8px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-stethoscope"></i> Nouvelle Consultation</h1>
            <a href="${pageContext.request.contextPath}/generaliste/consultation" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Retour
            </a>
        </div>

        <!-- Error Message -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle"></i> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Form Card -->
        <div class="card">
            <h4 class="mb-4"><i class="fas fa-file-medical"></i> Informations de Consultation</h4>

            <form method="POST" action="${pageContext.request.contextPath}/generaliste/consultation">

                <!-- Patient Selection -->
                <div class="mb-4">
                    <label for="patientId" class="form-label">
                        <i class="fas fa-user-injured text-primary"></i> Patient *
                    </label>
                    <select class="form-select" id="patientId" name="patientId" required>
                        <option value="">-- Sélectionner un patient --</option>
                        <c:forEach items="${patients}" var="patient">
                            <option value="${patient.id}">
                                ${patient.prenom} ${patient.nom} - ${patient.numeroSecuriteSociale}
                                (Né(e) le ${patient.dateNaissance})
                            </option>
                        </c:forEach>
                    </select>
                    <small class="text-muted">
                        <i class="fas fa-info-circle"></i>
                        Sélectionnez le patient pour cette consultation
                    </small>
                </div>

                <!-- Motif -->
                <div class="mb-4">
                    <label for="motif" class="form-label">
                        <i class="fas fa-clipboard-list text-warning"></i> Motif de Consultation *
                    </label>
                    <textarea class="form-control" id="motif" name="motif" rows="3"
                              placeholder="Ex: Douleurs abdominales, Fièvre persistante, Contrôle de routine..."
                              required></textarea>
                    <small class="text-muted">
                        <i class="fas fa-info-circle"></i>
                        Décrivez brièvement la raison de la visite
                    </small>
                </div>

                <!-- Observations -->
                <div class="mb-4">
                    <label for="observations" class="form-label">
                        <i class="fas fa-notes-medical text-info"></i> Observations Cliniques
                    </label>
                    <textarea class="form-control" id="observations" name="observations" rows="5"
                              placeholder="Ex: Examen physique, symptômes observés, mesures prises..."></textarea>
                    <small class="text-muted">
                        <i class="fas fa-info-circle"></i>
                        Notez vos observations médicales (optionnel)
                    </small>
                </div>

                <!-- Cost Information -->
                <div class="cost-info">
                    <h5 class="mb-2">
                        <i class="fas fa-money-bill-wave"></i> Coût de la Consultation
                    </h5>
                    <p class="mb-0">
                        <strong style="font-size: 1.5rem; color: #27ae60;">150 DH</strong>
                        <span class="text-muted">(Tarif fixe)</span>
                    </p>
                </div>

                <!-- Submit Button -->
                <div class="mt-4">
                    <button type="submit" class="btn btn-submit">
                        <i class="fas fa-save"></i> Créer la Consultation
                    </button>
                </div>

                <div class="text-center mt-3">
                    <small class="text-muted">* Champs obligatoires</small>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

