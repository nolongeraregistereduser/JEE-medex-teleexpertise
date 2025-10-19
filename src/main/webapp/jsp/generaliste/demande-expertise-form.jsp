<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demande d'Expertise - Medex</title>
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
        }
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            padding: 25px;
            margin-bottom: 20px;
        }
        .specialist-card {
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            padding: 20px;
            transition: all 0.3s;
            height: 100%;
        }
        .specialist-card:hover {
            border-color: #27ae60;
            box-shadow: 0 4px 12px rgba(46, 204, 113, 0.2);
        }
        .tarif-badge {
            background: #27ae60;
            color: white;
            padding: 8px 15px;
            border-radius: 20px;
            font-size: 1.1rem;
            font-weight: bold;
        }
        .slot-btn {
            margin: 5px;
        }
        .cost-breakdown {
            background: #e8f5e9;
            border-left: 4px solid #27ae60;
            padding: 15px;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1><i class="fas fa-user-md"></i> Demande d'Expertise</h1>
                    <p class="text-muted mb-0">
                        Patient: <strong>${consultation.patient.prenom} ${consultation.patient.nom}</strong> |
                        Spécialité: <strong>${specialty}</strong>
                    </p>
                </div>
                <a href="${pageContext.request.contextPath}/generaliste/consultation" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Retour
                </a>
            </div>
        </div>

        <!-- Step 2: Select Specialist -->
        <div class="card">
            <h4 class="mb-3">
                <i class="fas fa-users"></i>
                Étape 2: Spécialistes Disponibles (${specialists.size()})
                <span class="badge bg-info">Triés par tarif</span>
            </h4>

            <c:choose>
                <c:when test="${not empty specialists}">
                    <div class="row">
                        <c:forEach items="${specialists}" var="spec" varStatus="status">
                            <div class="col-md-4 mb-3">
                                <div class="specialist-card">
                                    <div class="text-center mb-3">
                                        <i class="fas fa-user-md fa-3x text-primary"></i>
                                    </div>
                                    <h5 class="text-center">${spec.nom} ${spec.prenom}</h5>

                                    <div class="text-center mb-3">
                                        <span class="tarif-badge">
                                            <i class="fas fa-money-bill-wave"></i> ${spec.tarif} DH
                                        </span>
                                    </div>

                                    <p class="text-muted text-center">
                                        <i class="fas fa-stethoscope"></i> ${spec.specialite}<br/>
                                        <i class="fas fa-clock"></i> ${spec.dureeConsultation} min
                                    </p>

                                    <c:if test="${status.index == 0}">
                                        <div class="alert alert-success text-center py-1">
                                            <small><i class="fas fa-star"></i> Tarif le plus bas</small>
                                        </div>
                                    </c:if>

                                    <div class="text-center">
                                        <a href="?consultationId=${consultation.id}&specialty=${specialty}&specialistId=${spec.id}"
                                           class="btn btn-primary w-100">
                                            <i class="fas fa-calendar-alt"></i> Voir Créneaux
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning">
                        <i class="fas fa-exclamation-triangle"></i>
                        Aucun spécialiste disponible pour cette spécialité.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Step 3: Select Time Slot and Submit Request -->
        <c:if test="${not empty selectedSpecialist}">
            <div class="card">
                <h4 class="mb-3">
                    <i class="fas fa-calendar-check"></i>
                    Étape 3: Créneaux Disponibles
                </h4>

                <div class="alert alert-info">
                    <strong>Spécialiste sélectionné:</strong> Dr. ${selectedSpecialist.nom} ${selectedSpecialist.prenom}
                    (${selectedSpecialist.tarif} DH)
                </div>

                <c:choose>
                    <c:when test="${not empty availableSlots}">
                        <form method="POST" action="${pageContext.request.contextPath}/generaliste/demande-expertise">
                            <input type="hidden" name="consultationId" value="${consultation.id}"/>
                            <input type="hidden" name="specialistId" value="${selectedSpecialist.id}"/>

                            <!-- Time Slot Selection -->
                            <div class="mb-4">
                                <label class="form-label"><strong>Sélectionnez un créneau:</strong></label>
                                <div class="row">
                                    <c:forEach items="${availableSlots}" var="creneau">
                                        <div class="col-md-3 mb-2">
                                            <input type="radio" class="btn-check" name="creneauId"
                                                   id="creneau${creneau.id}" value="${creneau.id}" required>
                                            <label class="btn btn-outline-success w-100" for="creneau${creneau.id}">
                                                <i class="fas fa-clock"></i><br/>
                                                <fmt:formatDate value="${creneau.dateHeureDebut}" pattern="dd/MM/yyyy"/><br/>
                                                <strong><fmt:formatDate value="${creneau.dateHeureDebut}" pattern="HH:mm"/> -
                                                <fmt:formatDate value="${creneau.dateHeureFin}" pattern="HH:mm"/></strong>
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <!-- Question Field -->
                            <div class="mb-4">
                                <label for="question" class="form-label">
                                    <i class="fas fa-question-circle"></i> <strong>Question pour le spécialiste *</strong>
                                </label>
                                <textarea class="form-control" id="question" name="question" rows="4"
                                          placeholder="Décrivez votre question ou préoccupation médicale pour le spécialiste..."
                                          required></textarea>
                            </div>

                            <!-- Priority Selection -->
                            <div class="mb-4">
                                <label class="form-label">
                                    <i class="fas fa-exclamation-triangle"></i> <strong>Niveau de Priorité *</strong>
                                </label>
                                <div class="row">
                                    <div class="col-md-4">
                                        <input type="radio" class="btn-check" name="priorite"
                                               id="urgente" value="URGENTE" required>
                                        <label class="btn btn-outline-danger w-100" for="urgente">
                                            <i class="fas fa-ambulance"></i><br/>
                                            <strong>URGENTE</strong><br/>
                                            <small>Réponse rapide nécessaire</small>
                                        </label>
                                    </div>
                                    <div class="col-md-4">
                                        <input type="radio" class="btn-check" name="priorite"
                                               id="normale" value="NORMALE" required checked>
                                        <label class="btn btn-outline-warning w-100" for="normale">
                                            <i class="fas fa-clock"></i><br/>
                                            <strong>NORMALE</strong><br/>
                                            <small>Délai standard</small>
                                        </label>
                                    </div>
                                    <div class="col-md-4">
                                        <input type="radio" class="btn-check" name="priorite"
                                               id="non_urgente" value="NON_URGENTE" required>
                                        <label class="btn btn-outline-success w-100" for="non_urgente">
                                            <i class="fas fa-check-circle"></i><br/>
                                            <strong>NON URGENTE</strong><br/>
                                            <small>Pas de rush</small>
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <!-- Cost Breakdown -->
                            <div class="cost-breakdown mb-4">
                                <h5 class="mb-3"><i class="fas fa-calculator"></i> Récapitulatif des Coûts</h5>
                                <table class="table table-borderless mb-0">
                                    <tr>
                                        <td><i class="fas fa-file-medical"></i> Consultation Généraliste</td>
                                        <td class="text-end"><strong>150 DH</strong></td>
                                    </tr>
                                    <tr>
                                        <td><i class="fas fa-user-md"></i> Avis Spécialiste (${selectedSpecialist.specialite})</td>
                                        <td class="text-end"><strong>${selectedSpecialist.tarif} DH</strong></td>
                                    </tr>
                                    <tr class="border-top">
                                        <td><strong><i class="fas fa-money-bill-wave"></i> TOTAL</strong></td>
                                        <td class="text-end">
                                            <h4 class="text-success mb-0">
                                                <strong>${150 + selectedSpecialist.tarif} DH</strong>
                                            </h4>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <!-- Submit Button -->
                            <button type="submit" class="btn btn-success btn-lg w-100">
                                <i class="fas fa-paper-plane"></i> Envoyer la Demande d'Expertise
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-warning">
                            <i class="fas fa-calendar-times"></i>
                            Aucun créneau disponible pour ce spécialiste. Veuillez en sélectionner un autre.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
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
    <title>Sélectionner Spécialité - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            padding: 20px;
        }
        .container { max-width: 1000px; }
        .header {
            background: white;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }
        .specialty-card {
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            padding: 20px;
            text-align: center;
            transition: all 0.3s;
            cursor: pointer;
            margin-bottom: 20px;
        }
        .specialty-card:hover {
            border-color: #27ae60;
            box-shadow: 0 4px 12px rgba(46, 204, 113, 0.3);
            transform: translateY(-5px);
        }
        .specialty-icon {
            font-size: 3rem;
            color: #27ae60;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><i class="fas fa-user-md"></i> Demander un Avis Spécialiste</h1>
            <p class="text-muted mb-0">Consultation pour: <strong>${consultation.patient.prenom} ${consultation.patient.nom}</strong></p>
        </div>

        <div class="card">
            <h4 class="mb-4"><i class="fas fa-stethoscope"></i> Étape 1: Sélectionnez la Spécialité</h4>

            <div class="row">
                <c:forEach items="${specialties}" var="specialty">
                    <div class="col-md-4">
                        <a href="?consultationId=${consultation.id}&specialty=${specialty}"
                           class="text-decoration-none">
                            <div class="specialty-card">
                                <i class="fas fa-heartbeat specialty-icon"></i>
                                <h5>${specialty}</h5>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

