<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enregistrement Patient - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #2c3e50;
            --secondary-color: #3498db;
            --success-color: #27ae60;
            --warning-color: #f39c12;
            --danger-color: #e74c3c;
            --light-bg: #ecf0f1;
        }

        body {
            background: linear-gradient(135deg, var(--light-bg) 0%, #bdc3c7 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
        }

        .navbar {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--secondary-color) 100%);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .card:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.15);
        }

        .card-header {
            border-radius: 15px 15px 0 0 !important;
            padding: 1.5rem;
        }

        .btn {
            border-radius: 10px;
            padding: 0.75rem 1.5rem;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }

        .form-control {
            border-radius: 10px;
            border: 2px solid #e9ecef;
            padding: 0.75rem 1rem;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            border-color: var(--secondary-color);
            box-shadow: 0 0 0 0.2rem rgba(52, 152, 219, 0.25);
        }

        .alert {
            border-radius: 10px;
            border: none;
        }

        .patient-card {
            background: linear-gradient(135deg, #fff 0%, #f8f9fa 100%);
            border-left: 4px solid var(--success-color);
        }

        .search-section {
            background: linear-gradient(135deg, #fff 0%, #f1f3f4 100%);
        }

        .registration-section {
            background: linear-gradient(135deg, #fff 0%, #e8f4f8 100%);
        }

        .vitals-section {
            background: linear-gradient(135deg, #fff 0%, #e8f5e8 100%);
        }

        .icon-circle {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 1rem;
        }

        .stats-card {
            background: linear-gradient(135deg, var(--secondary-color) 0%, #5dade2 100%);
            color: white;
            border-radius: 15px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand d-flex align-items-center" href="#">
                <div class="icon-circle bg-white text-primary me-3">
                    <i class="fas fa-hospital-user"></i>
                </div>
                <div>
                    <h5 class="mb-0">Medex</h5>
                    <small class="opacity-75">Interface Infirmier</small>
                </div>
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link btn btn-outline-light me-2" href="${pageContext.request.contextPath}/infirmier/dashboard">
                    <i class="fas fa-tachometer-alt me-1"></i>Tableau de bord
                </a>
                <a class="nav-link btn btn-light text-dark" href="${pageContext.request.contextPath}/logout">
                    <i class="fas fa-sign-out-alt me-1"></i>Déconnexion
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row mb-4">
            <div class="col-12">
                <div class="d-flex align-items-center mb-3">
                    <div class="icon-circle bg-primary text-white">
                        <i class="fas fa-user-plus"></i>
                    </div>
                    <div>
                        <h2 class="mb-0 text-primary">Accueil Patient</h2>
                        <p class="mb-0 text-muted">Rechercher ou enregistrer un nouveau patient</p>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i>
                <strong>Erreur!</strong> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle me-2"></i>
                <strong>Succès!</strong> ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Search Section -->
        <div class="row mb-4">
            <div class="col-lg-6">
                <div class="card search-section">
                    <div class="card-header bg-info text-white">
                        <div class="d-flex align-items-center">
                            <i class="fas fa-search me-2"></i>
                            <h5 class="mb-0">Rechercher un Patient</h5>
                        </div>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                            <input type="hidden" name="action" value="search">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="nom" class="form-label">
                                        <i class="fas fa-user me-1"></i>Nom
                                    </label>
                                    <input type="text" class="form-control" id="nom" name="nom"
                                           value="${searchNom}" placeholder="Nom de famille">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="prenom" class="form-label">
                                        <i class="fas fa-user me-1"></i>Prénom
                                    </label>
                                    <input type="text" class="form-control" id="prenom" name="prenom"
                                           value="${searchPrenom}" placeholder="Prénom">
                                </div>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-info">
                                    <i class="fas fa-search me-1"></i>Rechercher Patient
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Search Results -->
            <c:if test="${searchResult == 'found'}">
                <div class="col-lg-6">
                    <div class="card patient-card">
                        <div class="card-header bg-success text-white">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-user-check me-2"></i>
                                <h5 class="mb-0">Patient(s) Trouvé(s)</h5>
                            </div>
                        </div>
                        <div class="card-body">
                            <c:forEach var="patient" items="${patients}" varStatus="status">
                                <div class="border rounded p-3 mb-3 <c:if test='${status.last}'>mb-0</c:if>">
                                    <div class="d-flex justify-content-between align-items-start">
                                        <div>
                                            <h6 class="text-primary">${patient.nomComplet}</h6>
                                            <p class="mb-1"><i class="fas fa-calendar me-1"></i><strong>Né(e) le:</strong> ${patient.dateNaissance}</p>
                                            <c:if test="${not empty patient.telephone}">
                                                <p class="mb-1"><i class="fas fa-phone me-1"></i><strong>Tél:</strong> ${patient.telephone}</p>
                                            </c:if>
                                            <c:if test="${not empty patient.mutuelle}">
                                                <p class="mb-1"><i class="fas fa-shield-alt me-1"></i><strong>Mutuelle:</strong> ${patient.mutuelle}</p>
                                            </c:if>
                                        </div>
                                        <button class="btn btn-success btn-sm" onclick="showVitalsForm(${patient.id})">
                                            <i class="fas fa-heart me-1"></i>Signes Vitaux
                                        </button>
                                    </div>

                                    <c:if test="${not empty latestVitals && patients.size() == 1}">
                                        <div class="mt-3 p-2 bg-light rounded">
                                            <h6 class="text-muted mb-2">Derniers signes vitaux:</h6>
                                            <div class="row text-sm">
                                                <div class="col-4">
                                                    <i class="fas fa-heartbeat text-danger"></i> ${latestVitals.tensionArterielle}
                                                </div>
                                                <div class="col-4">
                                                    <i class="fas fa-heart text-primary"></i> ${latestVitals.frequenceCardiaque} bpm
                                                </div>
                                                <div class="col-4">
                                                    <i class="fas fa-thermometer-half text-warning"></i> ${latestVitals.temperature}°C
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${searchResult == 'notFound'}">
                <div class="col-lg-6">
                    <div class="card border-warning">
                        <div class="card-header bg-warning text-dark">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                <h5 class="mb-0">Patient Non Trouvé</h5>
                            </div>
                        </div>
                        <div class="card-body text-center">
                            <i class="fas fa-user-slash fa-3x text-muted mb-3"></i>
                            <p class="mb-3">Aucun patient trouvé avec ces critères.</p>
                            <button class="btn btn-warning" onclick="showRegistrationForm()">
                                <i class="fas fa-user-plus me-1"></i>Enregistrer Nouveau Patient
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <!-- Registration Form -->
        <div class="row mb-4" id="registrationForm" style="display: none;">
            <div class="col-12">
                <div class="card registration-section">
                    <div class="card-header bg-primary text-white">
                        <div class="d-flex align-items-center">
                            <i class="fas fa-user-plus me-2"></i>
                            <h5 class="mb-0">Enregistrer Nouveau Patient</h5>
                        </div>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                            <input type="hidden" name="action" value="register">

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="nomReg" class="form-label">
                                        <i class="fas fa-user me-1"></i>Nom *
                                    </label>
                                    <input type="text" class="form-control" id="nomReg" name="nom" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="prenomReg" class="form-label">
                                        <i class="fas fa-user me-1"></i>Prénom *
                                    </label>
                                    <input type="text" class="form-control" id="prenomReg" name="prenom" required>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="dateNaissance" class="form-label">
                                        <i class="fas fa-calendar me-1"></i>Date de Naissance *
                                    </label>
                                    <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="telephone" class="form-label">
                                        <i class="fas fa-phone me-1"></i>Téléphone
                                    </label>
                                    <input type="tel" class="form-control" id="telephone" name="telephone"
                                           placeholder="Ex: 0612345678">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="mutuelle" class="form-label">
                                        <i class="fas fa-shield-alt me-1"></i>Mutuelle
                                    </label>
                                    <input type="text" class="form-control" id="mutuelle" name="mutuelle"
                                           placeholder="Ex: CNOPS, CNSS, RAMED">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="adresse" class="form-label">
                                    <i class="fas fa-map-marker-alt me-1"></i>Adresse
                                </label>
                                <textarea class="form-control" id="adresse" name="adresse" rows="2"
                                          placeholder="Adresse complète du patient"></textarea>
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fas fa-save me-1"></i>Enregistrer Patient
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Vital Signs Form -->
        <c:if test="${showVitalsForm or not empty patient}">
            <div class="row" id="vitalsForm">
                <div class="col-12">
                    <div class="card vitals-section">
                        <div class="card-header bg-success text-white">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-heartbeat me-2"></i>
                                <h5 class="mb-0">Signes Vitaux - ${patient.nomComplet}</h5>
                            </div>
                        </div>
                        <div class="card-body">
                            <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                                <input type="hidden" name="action" value="addVitals">
                                <input type="hidden" name="patientId" value="${patient.id}">

                                <div class="row">
                                    <div class="col-md-4 mb-3">
                                        <label for="tension" class="form-label">
                                            <i class="fas fa-heartbeat text-danger me-1"></i>Tension Artérielle *
                                        </label>
                                        <input type="text" class="form-control" id="tension" name="tension"
                                               placeholder="Ex: 120/80" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label for="frequenceCardiaque" class="form-label">
                                            <i class="fas fa-heart text-primary me-1"></i>Fréquence Cardiaque * (bpm)
                                        </label>
                                        <input type="number" class="form-control" id="frequenceCardiaque"
                                               name="frequenceCardiaque" min="40" max="200" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label for="temperature" class="form-label">
                                            <i class="fas fa-thermometer-half text-warning me-1"></i>Température * (°C)
                                        </label>
                                        <input type="number" class="form-control" id="temperature" name="temperature"
                                               step="0.1" min="35" max="45" required>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-4 mb-3">
                                        <label for="frequenceRespiratoire" class="form-label">
                                            <i class="fas fa-lungs text-info me-1"></i>Fréquence Respiratoire * (rpm)
                                        </label>
                                        <input type="number" class="form-control" id="frequenceRespiratoire"
                                               name="frequenceRespiratoire" min="10" max="40" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label for="poids" class="form-label">
                                            <i class="fas fa-weight text-secondary me-1"></i>Poids (kg)
                                        </label>
                                        <input type="number" class="form-control" id="poids" name="poids"
                                               step="0.1" min="1" max="300">
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label for="taille" class="form-label">
                                            <i class="fas fa-ruler-vertical text-secondary me-1"></i>Taille (cm)
                                        </label>
                                        <input type="number" class="form-control" id="taille" name="taille"
                                               min="50" max="250">
                                    </div>
                                </div>

                                <div class="d-grid">
                                    <button type="submit" class="btn btn-success btn-lg">
                                        <i class="fas fa-plus me-1"></i>Enregistrer et Ajouter à la File d'Attente
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function showRegistrationForm() {
            document.getElementById('registrationForm').style.display = 'block';
            document.getElementById('registrationForm').scrollIntoView({ behavior: 'smooth' });
        }

        function showVitalsForm(patientId) {
            const vitalsForm = document.getElementById('vitalsForm');
            if (vitalsForm) {
                vitalsForm.style.display = 'block';
                const patientIdInput = vitalsForm.querySelector('input[name="patientId"]');
                if (patientIdInput) {
                    patientIdInput.value = patientId;
                }
                vitalsForm.scrollIntoView({ behavior: 'smooth' });
            }
        }

        <c:if test="${searchResult == 'notFound'}">
            showRegistrationForm();
        </c:if>

        // Auto-hide alerts after 5 seconds
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>
