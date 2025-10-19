<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails Patient - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            padding: 20px;
        }
        .container { max-width: 1200px; }
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
            padding: 25px;
            margin-bottom: 20px;
        }
        .info-label {
            font-weight: 600;
            color: #555;
            margin-bottom: 5px;
        }
        .info-value {
            font-size: 1.1rem;
            color: #333;
            margin-bottom: 15px;
        }
        .vital-sign-card {
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 15px;
            transition: all 0.3s;
        }
        .vital-sign-card:hover {
            border-color: #3498db;
            box-shadow: 0 4px 12px rgba(52, 152, 219, 0.2);
        }
        .vital-icon {
            font-size: 2rem;
            color: #3498db;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-user-circle"></i> Détails du Patient</h1>
            <a href="${pageContext.request.contextPath}/infirmier/patients" class="btn btn-primary">
                <i class="fas fa-arrow-left"></i> Retour à la liste
            </a>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <c:if test="${not empty patient}">
            <!-- Patient Information -->
            <div class="card">
                <h3 class="mb-4"><i class="fas fa-id-card text-primary"></i> Informations Administratives</h3>
                <div class="row">
                    <div class="col-md-6">
                        <div class="info-label">Nom Complet</div>
                        <div class="info-value">
                            <strong>${patient.prenom} ${patient.nom}</strong>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">Date de Naissance</div>
                        <div class="info-value">${patient.dateNaissance}</div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">Numéro de Sécurité Sociale</div>
                        <div class="info-value"><code>${patient.numeroSecuriteSociale}</code></div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">Téléphone</div>
                        <div class="info-value">
                            <c:choose>
                                <c:when test="${not empty patient.telephone}">
                                    <i class="fas fa-phone"></i> ${patient.telephone}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">Non renseigné</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">Adresse</div>
                        <div class="info-value">
                            <c:choose>
                                <c:when test="${not empty patient.adresse}">
                                    <i class="fas fa-map-marker-alt"></i> ${patient.adresse}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">Non renseignée</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">Mutuelle</div>
                        <div class="info-value">
                            <c:choose>
                                <c:when test="${not empty patient.mutuelle}">
                                    ${patient.mutuelle}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">Non renseignée</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Medical Record -->
            <c:if test="${not empty dossierMedical}">
                <div class="card">
                    <h3 class="mb-4"><i class="fas fa-file-medical text-danger"></i> Dossier Médical</h3>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="info-label">Antécédents</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty dossierMedical.antecedents}">
                                        ${dossierMedical.antecedents}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">Aucun antécédent enregistré</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="info-label">Allergies</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty dossierMedical.allergies}">
                                        <span class="badge bg-warning text-dark">
                                            <i class="fas fa-exclamation-triangle"></i> ${dossierMedical.allergies}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">Aucune allergie connue</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="info-label">Traitement en Cours</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty dossierMedical.traitementEnCours}">
                                        ${dossierMedical.traitementEnCours}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">Aucun traitement en cours</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <!-- Vital Signs History -->
            <div class="card">
                <h3 class="mb-4"><i class="fas fa-heartbeat text-danger"></i> Historique des Signes Vitaux</h3>

                <c:choose>
                    <c:when test="${not empty signesVitauxList}">
                        <div class="row">
                            <c:forEach items="${signesVitauxList}" var="signes" varStatus="status">
                                <div class="col-md-12">
                                    <div class="vital-sign-card">
                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                            <h5 class="mb-0">
                                                <i class="fas fa-calendar-alt text-primary"></i>
                                                Mesure du <fmt:formatDate value="${signes.dateSaisie}" pattern="dd/MM/yyyy à HH:mm" />
                                            </h5>
                                            <c:if test="${status.index == 0}">
                                                <span class="badge bg-success">Plus récent</span>
                                            </c:if>
                                        </div>

                                        <div class="row">
                                            <c:if test="${not empty signes.tension}">
                                                <div class="col-md-4 col-sm-6 mb-3">
                                                    <div class="d-flex align-items-center">
                                                        <i class="fas fa-tachometer-alt vital-icon me-3"></i>
                                                        <div>
                                                            <small class="text-muted">Tension Artérielle</small>
                                                            <div class="fw-bold">${signes.tension}</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>

                                            <c:if test="${not empty signes.frequenceCardiaque}">
                                                <div class="col-md-4 col-sm-6 mb-3">
                                                    <div class="d-flex align-items-center">
                                                        <i class="fas fa-heartbeat vital-icon me-3"></i>
                                                        <div>
                                                            <small class="text-muted">Fréquence Cardiaque</small>
                                                            <div class="fw-bold">${signes.frequenceCardiaque} bpm</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>

                                            <c:if test="${not empty signes.temperature}">
                                                <div class="col-md-4 col-sm-6 mb-3">
                                                    <div class="d-flex align-items-center">
                                                        <i class="fas fa-thermometer-half vital-icon me-3"></i>
                                                        <div>
                                                            <small class="text-muted">Température</small>
                                                            <div class="fw-bold">${signes.temperature}°C</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>

                                            <c:if test="${not empty signes.frequenceRespiratoire}">
                                                <div class="col-md-4 col-sm-6 mb-3">
                                                    <div class="d-flex align-items-center">
                                                        <i class="fas fa-lungs vital-icon me-3"></i>
                                                        <div>
                                                            <small class="text-muted">Fréquence Respiratoire</small>
                                                            <div class="fw-bold">${signes.frequenceRespiratoire} /min</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>

                                            <c:if test="${not empty signes.poids}">
                                                <div class="col-md-4 col-sm-6 mb-3">
                                                    <div class="d-flex align-items-center">
                                                        <i class="fas fa-weight vital-icon me-3"></i>
                                                        <div>
                                                            <small class="text-muted">Poids</small>
                                                            <div class="fw-bold">${signes.poids} kg</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>

                                            <c:if test="${not empty signes.taille}">
                                                <div class="col-md-4 col-sm-6 mb-3">
                                                    <div class="d-flex align-items-center">
                                                        <i class="fas fa-ruler-vertical vital-icon me-3"></i>
                                                        <div>
                                                            <small class="text-muted">Taille</small>
                                                            <div class="fw-bold">${signes.taille} cm</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center py-4">
                            <i class="fas fa-heartbeat fa-3x text-muted mb-3"></i>
                            <p class="text-muted">Aucun signe vital enregistré pour ce patient</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

