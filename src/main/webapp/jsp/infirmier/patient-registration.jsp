<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil Patient - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary: #2563eb;
            --primary-dark: #1e40af;
            --success: #10b981;
            --danger: #ef4444;
            --warning: #f59e0b;
            --light: #f8fafc;
            --border: #e2e8f0;
        }

        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Inter', 'Segoe UI', sans-serif;
        }

        .navbar {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
        }

        .main-container {
            max-width: 900px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .card {
            background: white;
            border-radius: 16px;
            box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
            border: none;
            overflow: hidden;
        }

        .card-header-custom {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            color: white;
            padding: 2rem;
            border: none;
        }

        .card-header-custom h4 {
            margin: 0;
            font-weight: 600;
            font-size: 1.5rem;
        }

        .card-body-custom {
            padding: 2rem;
        }

        .form-control, .form-select {
            border: 2px solid var(--border);
            border-radius: 8px;
            padding: 0.75rem 1rem;
            transition: all 0.3s;
        }

        .form-control:focus, .form-select:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
        }

        .btn {
            border-radius: 8px;
            padding: 0.75rem 1.5rem;
            font-weight: 500;
            transition: all 0.3s;
            border: none;
        }

        .btn-primary {
            background: var(--primary);
        }

        .btn-primary:hover {
            background: var(--primary-dark);
            transform: translateY(-2px);
            box-shadow: 0 10px 15px -3px rgba(37, 99, 235, 0.3);
        }

        .btn-success {
            background: var(--success);
        }

        .btn-success:hover {
            background: #059669;
            transform: translateY(-2px);
        }

        .search-box {
            background: white;
            border-radius: 16px;
            padding: 2.5rem;
            box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
            margin-bottom: 2rem;
        }

        .search-input-wrapper {
            position: relative;
            margin-bottom: 1.5rem;
        }

        .search-input-wrapper i {
            position: absolute;
            left: 1rem;
            top: 50%;
            transform: translateY(-50%);
            color: #94a3b8;
        }

        .search-input-wrapper input {
            padding-left: 3rem;
            font-size: 1.1rem;
        }

        .patient-info-card {
            background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%);
            border-left: 4px solid var(--primary);
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
        }

        .info-row {
            display: flex;
            align-items: center;
            margin-bottom: 0.75rem;
        }

        .info-row i {
            width: 24px;
            color: var(--primary);
            margin-right: 0.5rem;
        }

        .alert {
            border-radius: 12px;
            border: none;
            padding: 1rem 1.5rem;
        }

        .section-title {
            font-size: 1.1rem;
            font-weight: 600;
            color: #334155;
            margin-bottom: 1rem;
            display: flex;
            align-items: center;
        }

        .section-title i {
            margin-right: 0.5rem;
            color: var(--primary);
        }

        .vitals-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
            margin-top: 1rem;
        }

        .vital-card {
            background: #f8fafc;
            border-radius: 8px;
            padding: 1rem;
            text-align: center;
            border: 2px solid var(--border);
        }

        .vital-icon {
            font-size: 1.5rem;
            margin-bottom: 0.5rem;
        }

        .vital-value {
            font-size: 1.25rem;
            font-weight: 600;
            color: #1e293b;
        }

        .vital-label {
            font-size: 0.875rem;
            color: #64748b;
        }

        .form-section {
            background: #f8fafc;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/infirmier/dashboard">
                <i class="fas fa-hospital-user text-primary me-2" style="font-size: 1.5rem;"></i>
                <div>
                    <strong>Medex</strong>
                    <small class="d-block text-muted" style="font-size: 0.75rem;">Espace Infirmier</small>
                </div>
            </a>
            <div class="navbar-nav ms-auto">
                <a class="btn btn-outline-primary btn-sm me-2" href="${pageContext.request.contextPath}/infirmier/dashboard">
                    <i class="fas fa-arrow-left me-1"></i>Retour
                </a>
                <a class="btn btn-outline-danger btn-sm" href="${pageContext.request.contextPath}/logout">
                    <i class="fas fa-sign-out-alt me-1"></i>Déconnexion
                </a>
            </div>
        </div>
    </nav>

    <div class="main-container">
        <!-- Alerts -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>
                <strong>Erreur:</strong> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle me-2"></i>
                <strong>Succès:</strong> ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Search Box (always visible unless patient found) -->
        <c:if test="${empty patient}">
            <div class="search-box">
                <h4 class="text-center mb-4">
                    <i class="fas fa-search text-primary me-2"></i>
                    Rechercher un Patient
                </h4>
                <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                    <input type="hidden" name="action" value="search">
                    <div class="search-input-wrapper">
                        <i class="fas fa-id-card"></i>
                        <input type="text"
                               class="form-control form-control-lg"
                               name="ssn"
                               placeholder="Entrez le numéro de sécurité sociale"
                               value="${searchSSN}"
                               required
                               autofocus>
                    </div>
                    <button type="submit" class="btn btn-primary w-100 btn-lg">
                        <i class="fas fa-search me-2"></i>Rechercher
                    </button>
                </form>
            </div>
        </c:if>

        <!-- Patient Not Found - Show Registration Form -->
        <c:if test="${searchResult == 'notFound'}">
            <div class="card">
                <div class="card-header-custom">
                    <h4>
                        <i class="fas fa-user-plus me-2"></i>
                        Enregistrer un Nouveau Patient
                    </h4>
                </div>
                <div class="card-body-custom">
                    <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                        <input type="hidden" name="action" value="register">

                        <!-- Patient Info Section -->
                        <div class="form-section">
                            <div class="section-title">
                                <i class="fas fa-user"></i>
                                Informations du Patient
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Nom <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" name="nom" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Prénom <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" name="prenom" required>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Date de Naissance <span class="text-danger">*</span></label>
                                    <input type="date" class="form-control" name="dateNaissance" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">N° Sécurité Sociale <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" name="numeroSecuriteSociale"
                                           value="${searchSSN}" required>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Téléphone</label>
                                    <input type="tel" class="form-control" name="telephone"
                                           placeholder="Ex: 0612345678">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Mutuelle</label>
                                    <input type="text" class="form-control" name="mutuelle"
                                           placeholder="Ex: CNOPS, CNSS">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Adresse</label>
                                <textarea class="form-control" name="adresse" rows="2"
                                          placeholder="Adresse complète"></textarea>
                            </div>
                        </div>

                        <!-- Vital Signs Section -->
                        <div class="form-section">
                            <div class="section-title">
                                <i class="fas fa-heartbeat"></i>
                                Signes Vitaux <span class="text-danger">*</span>
                            </div>

                            <div class="row">
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Tension Artérielle</label>
                                    <input type="text" class="form-control" name="tension"
                                           placeholder="120/80" required>
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Fréquence Cardiaque (bpm)</label>
                                    <input type="number" class="form-control" name="frequenceCardiaque"
                                           min="30" max="220" required>
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Température (°C)</label>
                                    <input type="number" class="form-control" name="temperature"
                                           step="0.1" min="30" max="45" required>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Fréquence Respiratoire (rpm)</label>
                                    <input type="number" class="form-control" name="frequenceRespiratoire"
                                           min="5" max="60" required>
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Poids (kg)</label>
                                    <input type="number" class="form-control" name="poids"
                                           step="0.1" min="1" max="300">
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Taille (cm)</label>
                                    <input type="number" class="form-control" name="taille"
                                           min="30" max="250">
                                </div>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success w-100 btn-lg">
                            <i class="fas fa-save me-2"></i>
                            Enregistrer et Ajouter à la File d'Attente
                        </button>
                    </form>
                </div>
            </div>
        </c:if>

        <!-- Patient Found - Show Patient Info and Vitals Form -->
        <c:if test="${not empty patient}">
            <div class="card mb-3">
                <div class="card-header-custom">
                    <h4>
                        <i class="fas fa-user-check me-2"></i>
                        Patient: ${patient.nomComplet}
                    </h4>
                </div>
                <div class="card-body-custom">
                    <!-- Patient Information -->
                    <div class="patient-info-card">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="info-row">
                                    <i class="fas fa-id-card"></i>
                                    <strong>N° Sécu:</strong>&nbsp;${patient.numeroSecuriteSociale}
                                </div>
                                <div class="info-row">
                                    <i class="fas fa-calendar"></i>
                                    <strong>Date de naissance:</strong>&nbsp;${patient.dateNaissance}
                                </div>
                                <c:if test="${not empty patient.telephone}">
                                    <div class="info-row">
                                        <i class="fas fa-phone"></i>
                                        <strong>Téléphone:</strong>&nbsp;${patient.telephone}
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-md-6">
                                <c:if test="${not empty patient.mutuelle}">
                                    <div class="info-row">
                                        <i class="fas fa-shield-alt"></i>
                                        <strong>Mutuelle:</strong>&nbsp;${patient.mutuelle}
                                    </div>
                                </c:if>
                                <c:if test="${not empty patient.adresse}">
                                    <div class="info-row">
                                        <i class="fas fa-map-marker-alt"></i>
                                        <strong>Adresse:</strong>&nbsp;${patient.adresse}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <!-- Latest Vital Signs -->
                    <c:if test="${not empty latestVitals}">
                        <div class="section-title">
                            <i class="fas fa-chart-line"></i>
                            Derniers Signes Vitaux
                        </div>
                        <div class="vitals-grid">
                            <div class="vital-card">
                                <div class="vital-icon text-danger">
                                    <i class="fas fa-heartbeat"></i>
                                </div>
                                <div class="vital-value">${latestVitals.tensionArterielle}</div>
                                <div class="vital-label">Tension</div>
                            </div>
                            <div class="vital-card">
                                <div class="vital-icon text-primary">
                                    <i class="fas fa-heart"></i>
                                </div>
                                <div class="vital-value">${latestVitals.frequenceCardiaque}</div>
                                <div class="vital-label">FC (bpm)</div>
                            </div>
                            <div class="vital-card">
                                <div class="vital-icon text-warning">
                                    <i class="fas fa-thermometer-half"></i>
                                </div>
                                <div class="vital-value">${latestVitals.temperature}°C</div>
                                <div class="vital-label">Température</div>
                            </div>
                            <div class="vital-card">
                                <div class="vital-icon text-info">
                                    <i class="fas fa-lungs"></i>
                                </div>
                                <div class="vital-value">${latestVitals.frequenceRespiratoire}</div>
                                <div class="vital-label">FR (rpm)</div>
                            </div>
                        </div>
                    </c:if>

                    <!-- Dossier Medical Information -->
                    <c:if test="${not empty dossier}">
                        <div class="mt-4">
                            <div class="section-title">
                                <i class="fas fa-file-medical"></i>
                                Dossier Médical
                            </div>
                            <div class="patient-info-card">
                                <c:if test="${not empty dossier.antecedents}">
                                    <div class="info-row">
                                        <i class="fas fa-history"></i>
                                        <strong>Antécédents:</strong>&nbsp;${dossier.antecedents}
                                    </div>
                                </c:if>
                                <c:if test="${not empty dossier.allergies}">
                                    <div class="info-row">
                                        <i class="fas fa-exclamation-triangle"></i>
                                        <strong>Allergies:</strong>&nbsp;${dossier.allergies}
                                    </div>
                                </c:if>
                                <c:if test="${not empty dossier.traitementEnCours}">
                                    <div class="info-row">
                                        <i class="fas fa-pills"></i>
                                        <strong>Traitement:</strong>&nbsp;${dossier.traitementEnCours}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:if>

                    <!-- Add New Vital Signs Form -->
                    <div class="mt-4">
                        <div class="section-title">
                            <i class="fas fa-plus-circle"></i>
                            Nouveaux Signes Vitaux
                        </div>
                        <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                            <input type="hidden" name="action" value="addVitals">
                            <input type="hidden" name="patientId" value="${patient.id}">

                            <div class="form-section">
                                <div class="row">
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Tension Artérielle <span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" name="tension"
                                               placeholder="120/80" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Fréquence Cardiaque <span class="text-danger">*</span></label>
                                        <input type="number" class="form-control" name="frequenceCardiaque"
                                               min="40" max="200" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Température (°C) <span class="text-danger">*</span></label>
                                        <input type="number" class="form-control" name="temperature"
                                               step="0.1" min="35" max="45" required>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Fréquence Respiratoire <span class="text-danger">*</span></label>
                                        <input type="number" class="form-control" name="frequenceRespiratoire"
                                               min="10" max="40" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Poids (kg)</label>
                                        <input type="number" class="form-control" name="poids"
                                               step="0.1" min="1" max="300">
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Taille (cm)</label>
                                        <input type="number" class="form-control" name="taille"
                                               min="50" max="250">
                                    </div>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-success w-100 btn-lg">
                                <i class="fas fa-check me-2"></i>
                                Enregistrer et Ajouter à la File d'Attente
                            </button>
                        </form>
                    </div>

                    <!-- Option to search another patient -->
                    <div class="text-center mt-4">
                        <a href="${pageContext.request.contextPath}/infirmier/patient-registration"
                           class="btn btn-outline-primary">
                            <i class="fas fa-search me-2"></i>
                            Rechercher un Autre Patient
                        </a>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
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

