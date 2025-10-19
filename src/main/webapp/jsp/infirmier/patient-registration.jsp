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
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 20px;
        }
        .container { max-width: 900px; }
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
            border: none;
            margin-bottom: 20px;
        }
        .card-header {
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            color: white;
            padding: 1.5rem;
            border-radius: 15px 15px 0 0;
        }
        .btn-primary {
            background: #3498db;
            border: none;
        }
        .btn-primary:hover {
            background: #2980b9;
        }
        .form-label {
            font-weight: 600;
            color: #2c3e50;
        }
        .alert {
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="text-center mb-4">
            <a href="${pageContext.request.contextPath}/infirmier/dashboard" class="btn btn-light">
                <i class="fas fa-arrow-left"></i> Retour au Tableau de Bord
            </a>
        </div>

        <!-- Error/Success Messages -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-circle"></i> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${patientNotFound}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <i class="fas fa-info-circle"></i> Patient non trouvé. Veuillez créer un nouveau dossier.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Search Patient Card -->
        <div class="card">
            <div class="card-header">
                <h4 class="mb-0"><i class="fas fa-search"></i> Rechercher un Patient</h4>
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                    <input type="hidden" name="action" value="search">
                    <div class="input-group mb-3">
                        <span class="input-group-text"><i class="fas fa-id-card"></i></span>
                        <input type="text" class="form-control" name="numeroSecuriteSociale"
                               placeholder="Numéro de sécurité sociale"
                               value="${numeroSecuriteSociale}" required>
                        <button class="btn btn-primary" type="submit">
                            <i class="fas fa-search"></i> Rechercher
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Patient Registration Form -->
        <div class="card">
            <div class="card-header">
                <h4 class="mb-0">
                    <i class="fas fa-user-plus"></i>
                    <c:choose>
                        <c:when test="${patientFound}">Enregistrer Signes Vitaux</c:when>
                        <c:otherwise>Nouveau Patient</c:otherwise>
                    </c:choose>
                </h4>
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/infirmier/patient-registration">
                    <input type="hidden" name="action" value="register">

                    <c:if test="${patientFound}">
                        <input type="hidden" name="patientId" value="${patient.id}">

                        <!-- Display Patient Info -->
                        <div class="alert alert-success">
                            <h5><i class="fas fa-user-check"></i> Patient Trouvé</h5>
                            <p class="mb-0"><strong>${patient.prenom} ${patient.nom}</strong></p>
                            <small>Né(e) le: ${patient.dateNaissance} | N° Sécu: ${patient.numeroSecuriteSociale}</small>
                        </div>
                    </c:if>

                    <c:if test="${!patientFound}">
                        <!-- Patient Information Section -->
                        <h5 class="text-primary mb-3"><i class="fas fa-user"></i> Informations Patient</h5>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Nom *</label>
                                <input type="text" class="form-control" name="nom" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Prénom *</label>
                                <input type="text" class="form-control" name="prenom" required>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Date de Naissance *</label>
                                <input type="date" class="form-control" name="dateNaissance" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Numéro de Sécurité Sociale *</label>
                                <input type="text" class="form-control" name="numeroSecuriteSociale"
                                       value="${numeroSecuriteSociale}" required>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Téléphone</label>
                                <input type="tel" class="form-control" name="telephone">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Mutuelle</label>
                                <input type="text" class="form-control" name="mutuelle">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Adresse</label>
                            <textarea class="form-control" name="adresse" rows="2"></textarea>
                        </div>

                        <!-- Medical History Section -->
                        <h5 class="text-primary mb-3 mt-4"><i class="fas fa-notes-medical"></i> Dossier Médical</h5>
                        <div class="mb-3">
                            <label class="form-label">Antécédents</label>
                            <textarea class="form-control" name="antecedents" rows="2"
                                      placeholder="Maladies antérieures, opérations..."></textarea>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Allergies</label>
                            <textarea class="form-control" name="allergies" rows="2"
                                      placeholder="Allergies médicamenteuses, alimentaires..."></textarea>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Traitement en Cours</label>
                            <textarea class="form-control" name="traitementEnCours" rows="2"
                                      placeholder="Médicaments actuels..."></textarea>
                        </div>
                    </c:if>

                    <!-- Vital Signs Section -->
                    <h5 class="text-primary mb-3 mt-4"><i class="fas fa-heartbeat"></i> Signes Vitaux</h5>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Tension Artérielle</label>
                            <input type="text" class="form-control" name="tension"
                                   placeholder="Ex: 120/80">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Fréquence Cardiaque (bpm)</label>
                            <input type="number" class="form-control" name="frequenceCardiaque"
                                   placeholder="Ex: 72">
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Température (°C)</label>
                            <input type="number" step="0.1" class="form-control" name="temperature"
                                   placeholder="Ex: 37.2">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Fréquence Respiratoire (/min)</label>
                            <input type="number" class="form-control" name="frequenceRespiratoire"
                                   placeholder="Ex: 16">
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Poids (kg)</label>
                            <input type="number" step="0.1" class="form-control" name="poids"
                                   placeholder="Ex: 70.5">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Taille (cm)</label>
                            <input type="number" step="0.1" class="form-control" name="taille"
                                   placeholder="Ex: 175">
                        </div>
                    </div>

                    <div class="d-grid gap-2 mt-4">
                        <button type="submit" class="btn btn-primary btn-lg">
                            <i class="fas fa-save"></i> Enregistrer et Ajouter à la File d'Attente
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
