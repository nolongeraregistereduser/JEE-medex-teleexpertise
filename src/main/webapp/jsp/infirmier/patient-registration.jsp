<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enregistrement Patient - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">
                <i class="fas fa-hospital-user me-2"></i>Medex - Infirmier
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/infirmier/dashboard">
                    <i class="fas fa-tachometer-alt me-1"></i>Tableau de bord
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                    <i class="fas fa-sign-out-alt me-1"></i>Déconnexion
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <h2><i class="fas fa-user-plus me-2"></i>Accueil Patient</h2>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i>${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle me-2"></i>${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-info text-white">
                        <h5 class="mb-0"><i class="fas fa-search me-2"></i>Rechercher un Patient</h5>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                            <input type="hidden" name="action" value="search">
                            <div class="mb-3">
                                <label for="numSecu" class="form-label">Numéro de Sécurité Sociale</label>
                                <input type="text" class="form-control" id="numSecu" name="numSecu"
                                       value="${numSecu}" required placeholder="Ex: 1234567890123">
                            </div>
                            <button type="submit" class="btn btn-info">
                                <i class="fas fa-search me-1"></i>Rechercher
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <c:if test="${searchResult == 'found'}">
                <div class="col-md-6">
                    <div class="card border-success">
                        <div class="card-header bg-success text-white">
                            <h5 class="mb-0"><i class="fas fa-user-check me-2"></i>Patient Trouvé</h5>
                        </div>
                        <div class="card-body">
                            <p><strong>Nom:</strong> ${patient.nom}</p>
                            <p><strong>Prénom:</strong> ${patient.prenom}</p>
                            <p><strong>Date de naissance:</strong> ${patient.dateNaissance}</p>
                            <p><strong>Téléphone:</strong> ${patient.telephone}</p>

                            <c:if test="${not empty latestVitals}">
                                <h6 class="mt-3">Derniers signes vitaux:</h6>
                                <small class="text-muted">
                                    Tension: ${latestVitals.tensionArterielle} |
                                    Pouls: ${latestVitals.frequenceCardiaque} bpm |
                                    Temp: ${latestVitals.temperature}°C
                                </small>
                            </c:if>

                            <div class="mt-3">
                                <button class="btn btn-success" onclick="showVitalsForm(${patient.id})">
                                    <i class="fas fa-heart me-1"></i>Ajouter Signes Vitaux
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${searchResult == 'notFound'}">
                <div class="col-md-6">
                    <div class="card border-warning">
                        <div class="card-header bg-warning text-dark">
                            <h5 class="mb-0"><i class="fas fa-user-plus me-2"></i>Patient Non Trouvé</h5>
                        </div>
                        <div class="card-body">
                            <p>Aucun patient trouvé avec ce numéro. Voulez-vous l'enregistrer?</p>
                            <button class="btn btn-warning" onclick="showRegistrationForm()">
                                <i class="fas fa-user-plus me-1"></i>Nouveau Patient
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <div class="row mt-4" id="registrationForm" style="display: none;">
            <div class="col-12">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-user-plus me-2"></i>Enregistrer Nouveau Patient</h5>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                            <input type="hidden" name="action" value="register">

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="nom" class="form-label">Nom *</label>
                                        <input type="text" class="form-control" id="nom" name="nom" required>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="prenom" class="form-label">Prénom *</label>
                                        <input type="text" class="form-control" id="prenom" name="prenom" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="dateNaissance" class="form-label">Date de Naissance *</label>
                                        <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" required>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="numSecuReg" class="form-label">Numéro de Sécurité Sociale *</label>
                                        <input type="text" class="form-control" id="numSecuReg" name="numSecu"
                                               value="${numSecu}" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="telephone" class="form-label">Téléphone</label>
                                        <input type="tel" class="form-control" id="telephone" name="telephone"
                                               placeholder="Ex: 0612345678">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="mutuelle" class="form-label">Mutuelle</label>
                                        <input type="text" class="form-control" id="mutuelle" name="mutuelle"
                                               placeholder="Ex: CNOPS, CNSS">
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="adresse" class="form-label">Adresse</label>
                                <textarea class="form-control" id="adresse" name="adresse" rows="2"></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-save me-1"></i>Enregistrer Patient
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${showVitalsForm or not empty patient}">
            <div class="row mt-4" id="vitalsForm">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header bg-success text-white">
                            <h5 class="mb-0"><i class="fas fa-heart me-2"></i>Signes Vitaux</h5>
                        </div>
                        <div class="card-body">
                            <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                                <input type="hidden" name="action" value="addVitals">
                                <input type="hidden" name="patientId" value="${patient.id}">

                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="mb-3">
                                            <label for="tension" class="form-label">Tension Artérielle *</label>
                                            <input type="text" class="form-control" id="tension" name="tension"
                                                   placeholder="Ex: 120/80" required>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="mb-3">
                                            <label for="frequenceCardiaque" class="form-label">Fréquence Cardiaque * (bpm)</label>
                                            <input type="number" class="form-control" id="frequenceCardiaque"
                                                   name="frequenceCardiaque" min="40" max="200" required>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="mb-3">
                                            <label for="temperature" class="form-label">Température * (°C)</label>
                                            <input type="number" class="form-control" id="temperature" name="temperature"
                                                   step="0.1" min="35" max="45" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="mb-3">
                                            <label for="frequenceRespiratoire" class="form-label">Fréquence Respiratoire * (rpm)</label>
                                            <input type="number" class="form-control" id="frequenceRespiratoire"
                                                   name="frequenceRespiratoire" min="10" max="40" required>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="mb-3">
                                            <label for="poids" class="form-label">Poids (kg)</label>
                                            <input type="number" class="form-control" id="poids" name="poids"
                                                   step="0.1" min="1" max="300">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="mb-3">
                                            <label for="taille" class="form-label">Taille (cm)</label>
                                            <input type="number" class="form-control" id="taille" name="taille"
                                                   min="50" max="250">
                                        </div>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-success">
                                    <i class="fas fa-plus me-1"></i>Enregistrer et Ajouter à la File d'Attente
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function showRegistrationForm() {
            document.getElementById('registrationForm').style.display = 'block';
        }

        function showVitalsForm(patientId) {
            const vitalsForm = document.getElementById('vitalsForm');
            if (vitalsForm) {
                vitalsForm.style.display = 'block';
                const patientIdInput = vitalsForm.querySelector('input[name="patientId"]');
                if (patientIdInput) {
                    patientIdInput.value = patientId;
                }
            }
        }

        <c:if test="${searchResult == 'notFound'}">
            showRegistrationForm();
        </c:if>
    </script>
</body>
</html>
