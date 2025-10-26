<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails Consultation - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
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
        .info-row {
            display: flex;
            padding: 15px 0;
            border-bottom: 1px solid #ecf0f1;
        }
        .info-row:last-child {
            border-bottom: none;
        }
        .info-label {
            font-weight: bold;
            color: #7f8c8d;
            width: 200px;
            flex-shrink: 0;
        }
        .info-value {
            color: #2c3e50;
            flex-grow: 1;
        }
        .badge-status {
            padding: 8px 20px;
            border-radius: 20px;
            font-weight: 600;
            display: inline-block;
        }
        .badge-en-attente {
            background: #f39c12;
            color: white;
        }
        .badge-en-attente-avis {
            background: #3498db;
            color: white;
        }
        .badge-terminee {
            background: #27ae60;
            color: white;
        }
        .section-title {
            color: #27ae60;
            font-weight: bold;
            font-size: 1.3rem;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #27ae60;
        }
        .btn-action {
            margin: 5px;
        }
        .action-buttons {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
        .vital-signs-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-top: 15px;
        }
        .vital-sign-item {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            text-align: center;
        }
        .vital-sign-label {
            font-size: 0.85rem;
            color: #7f8c8d;
            margin-bottom: 5px;
        }
        .vital-sign-value {
            font-size: 1.5rem;
            font-weight: bold;
            color: #27ae60;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- Header -->
    <div class="header">
        <div>
            <h2><i class="fas fa-stethoscope"></i> Détails de la Consultation</h2>
            <p class="mb-0">Consultation #${consultation.id}</p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/generaliste/dashboard" class="btn btn-outline-secondary">
                <i class="fas fa-home"></i> Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/generaliste/consultation" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Retour à la liste
            </a>
        </div>
    </div>

    <!-- Consultation Status -->
    <div class="card">
        <div class="text-center">
            <h4>Statut de la consultation</h4>
            <c:choose>
                <c:when test="${consultation.status == 'EN_ATTENTE'}">
                    <span class="badge-status badge-en-attente">
                        <i class="fas fa-clock"></i> En Attente
                    </span>
                </c:when>
                <c:when test="${consultation.status == 'EN_ATTENTE_AVIS_SPECIALISTE'}">
                    <span class="badge-status badge-en-attente-avis">
                        <i class="fas fa-user-md"></i> En Attente Avis Spécialiste
                    </span>
                </c:when>
                <c:when test="${consultation.status == 'TERMINEE'}">
                    <span class="badge-status badge-terminee">
                        <i class="fas fa-check-circle"></i> Terminée
                    </span>
                </c:when>
            </c:choose>
        </div>
    </div>

    <!-- Patient Information -->
    <div class="card">
        <h4 class="section-title"><i class="fas fa-user"></i> Informations Patient</h4>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-id-card"></i> Nom Complet:</div>
            <div class="info-value">${consultation.patient.nom} ${consultation.patient.prenom}</div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-id-badge"></i> Numéro Sécurité Sociale:</div>
            <div class="info-value">${consultation.patient.numeroSecuriteSociale != null ? consultation.patient.numeroSecuriteSociale : 'N/A'}</div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-birthday-cake"></i> Date de Naissance:</div>
            <div class="info-value">
                ${consultation.patient.dateNaissance != null ? consultation.patient.dateNaissance : 'N/A'}
            </div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-phone"></i> Téléphone:</div>
            <div class="info-value">${consultation.patient.telephone != null ? consultation.patient.telephone : 'N/A'}</div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-map-marker-alt"></i> Adresse:</div>
            <div class="info-value">${consultation.patient.adresse != null ? consultation.patient.adresse : 'N/A'}</div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-shield-alt"></i> Mutuelle:</div>
            <div class="info-value">${consultation.patient.mutuelle != null ? consultation.patient.mutuelle : 'N/A'}</div>
        </div>
    </div>

    <!-- Consultation Details -->
    <div class="card">
        <h4 class="section-title"><i class="fas fa-file-medical"></i> Détails de la Consultation</h4>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-calendar"></i> Date de Consultation:</div>
            <div class="info-value">
                ${consultation.dateConsultation != null ? consultation.dateConsultation : 'N/A'}
            </div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-user-md"></i> Médecin Généraliste:</div>
            <div class="info-value">Dr. ${consultation.medecinGeneraliste.nom} ${consultation.medecinGeneraliste.prenom}</div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-clipboard"></i> Motif:</div>
            <div class="info-value">${consultation.motif != null ? consultation.motif : 'N/A'}</div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-notes-medical"></i> Observations:</div>
            <div class="info-value">${consultation.observations != null ? consultation.observations : 'Aucune observation'}</div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-diagnoses"></i> Diagnostic:</div>
            <div class="info-value">${consultation.diagnostic != null ? consultation.diagnostic : 'Non défini'}</div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-pills"></i> Traitement:</div>
            <div class="info-value">${consultation.traitement != null ? consultation.traitement : 'Non défini'}</div>
        </div>
        <div class="info-row">
            <div class="info-label"><i class="fas fa-money-bill-wave"></i> Coût:</div>
            <div class="info-value">
                <strong style="color: #27ae60; font-size: 1.2rem;">${consultation.cout} DH</strong>
            </div>
        </div>
    </div>

    <!-- Technical Acts (if any) -->
    <c:if test="${not empty consultation.actesTechniques}">
    <div class="card">
        <h4 class="section-title"><i class="fas fa-microscope"></i> Actes Techniques</h4>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Type d'Acte</th>
                    <th>Description</th>
                    <th>Coût</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="acte" items="${consultation.actesTechniques}">
                <tr>
                    <td>${acte.typeActe}</td>
                    <td>${acte.description != null ? acte.description : 'N/A'}</td>
                    <td>${acte.cout} DH</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    </c:if>

    <!-- Expertise Requests (if any) -->
    <c:if test="${not empty consultation.demandesExpertise}">
    <div class="card">
        <h4 class="section-title"><i class="fas fa-user-md"></i> Demandes d'Expertise</h4>
        <c:forEach var="demande" items="${consultation.demandesExpertise}">
        <div class="card mb-3" style="border-left: 4px solid #3498db;">
            <div class="info-row">
                <div class="info-label">Spécialiste:</div>
                <div class="info-value">Dr. ${demande.medecinSpecialiste.nom} ${demande.medecinSpecialiste.prenom}</div>
            </div>
            <div class="info-row">
                <div class="info-label">Spécialité:</div>
                <div class="info-value">${demande.medecinSpecialiste.specialite}</div>
            </div>
            <div class="info-row">
                <div class="info-label">Priorité:</div>
                <div class="info-value">
                    <c:choose>
                        <c:when test="${demande.priorite == 'URGENTE'}">
                            <span class="badge bg-danger">Urgente</span>
                        </c:when>
                        <c:when test="${demande.priorite == 'NORMALE'}">
                            <span class="badge bg-warning">Normale</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-secondary">Non Urgente</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="info-row">
                <div class="info-label">Question:</div>
                <div class="info-value">${demande.question}</div>
            </div>
            <div class="info-row">
                <div class="info-label">Statut:</div>
                <div class="info-value">
                    <c:choose>
                        <c:when test="${demande.status == 'EN_ATTENTE'}">
                            <span class="badge bg-warning">En Attente</span>
                        </c:when>
                        <c:when test="${demande.status == 'TERMINEE'}">
                            <span class="badge bg-success">Terminée</span>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <c:if test="${demande.avisMedecin != null}">
            <div class="info-row">
                <div class="info-label">Avis du Spécialiste:</div>
                <div class="info-value"><strong>${demande.avisMedecin}</strong></div>
            </div>
            </c:if>
            <c:if test="${demande.recommandations != null}">
            <div class="info-row">
                <div class="info-label">Recommandations:</div>
                <div class="info-value">${demande.recommandations}</div>
            </div>
            </c:if>
            <div class="info-row">
                <div class="info-label">Tarif Spécialiste:</div>
                <div class="info-value"><strong>${demande.medecinSpecialiste.tarif} DH</strong></div>
            </div>
        </div>
        </c:forEach>
    </div>
    </c:if>

    <!-- Action Buttons -->
    <div class="card">
        <h4 class="section-title"><i class="fas fa-cogs"></i> Actions</h4>
        <div class="action-buttons">
            <c:if test="${consultation.status == 'EN_ATTENTE'}">
                <a href="${pageContext.request.contextPath}/generaliste/demande-expertise?consultationId=${consultation.id}"
                   class="btn btn-primary btn-action">
                    <i class="fas fa-user-md"></i> Demander Avis Spécialiste
                </a>
                <button class="btn btn-success btn-action" data-bs-toggle="modal" data-bs-target="#closeConsultationModal">
                    <i class="fas fa-check"></i> Clôturer la Consultation
                </button>
            </c:if>
            <c:if test="${consultation.status == 'TERMINEE'}">
                <a href="${pageContext.request.contextPath}/generaliste/consultation?action=print&id=${consultation.id}"
                   class="btn btn-info btn-action" target="_blank">
                    <i class="fas fa-print"></i> Imprimer
                </a>
            </c:if>
        </div>
    </div>
</div>

<!-- Modal for closing consultation -->
<div class="modal fade" id="closeConsultationModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="${pageContext.request.contextPath}/generaliste/consultation">
                <div class="modal-header">
                    <h5 class="modal-title">Clôturer la Consultation</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="action" value="close">
                    <input type="hidden" name="consultationId" value="${consultation.id}">

                    <div class="mb-3">
                        <label class="form-label">Diagnostic *</label>
                        <textarea name="diagnostic" class="form-control" rows="3" required>${consultation.diagnostic}</textarea>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Traitement/Prescription *</label>
                        <textarea name="traitement" class="form-control" rows="4" required>${consultation.traitement}</textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-success">Clôturer</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
