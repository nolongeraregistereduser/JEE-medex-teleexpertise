<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>File d'Attente - Medex</title>
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
        }
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        .badge-waiting {
            background: #f39c12;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
        }
        .badge-taken {
            background: #27ae60;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
        }
        .vital-sign {
            font-size: 0.9rem;
            color: #7f8c8d;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="d-flex justify-content-between align-items-center">
                <h1><i class="fas fa-users"></i> File d'Attente</h1>
                <div>
                    <span class="badge bg-warning me-2">
                        <i class="fas fa-hourglass-half"></i> ${waitingCount} En Attente
                    </span>
                    <a href="${pageContext.request.contextPath}/generaliste/dashboard" class="btn btn-primary">
                        <i class="fas fa-home"></i> Dashboard
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${param.success == 'taken'}">
            <div class="alert alert-success alert-dismissible fade show">
                <i class="fas fa-check-circle"></i> Patient pris en charge avec succès!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="card">
            <h4 class="mb-3"><i class="fas fa-list"></i> Patients en Attente (${totalToday})</h4>

            <c:choose>
                <c:when test="${not empty queue}">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-success">
                                <tr>
                                    <th>Position</th>
                                    <th>Heure Arrivée</th>
                                    <th>Patient</th>
                                    <th>N° Sécurité Sociale</th>
                                    <th>Signes Vitaux</th>
                                    <th>Statut</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${queue}" var="file" varStatus="status">
                                    <tr>
                                        <td><strong>#${status.index + 1}</strong></td>
                                        <td>
                                            <i class="fas fa-clock"></i>
                                            ${file.dateArrivee.toLocalTime().toString().substring(0, 5)}
                                        </td>
                                        <td>
                                            <i class="fas fa-user-circle text-primary"></i>
                                            <strong>${file.patient.prenom} ${file.patient.nom}</strong>
                                        </td>
                                        <td><code>${file.patient.numeroSecuriteSociale}</code></td>
                                        <td>
                                            <div class="vital-sign">
                                                <i class="fas fa-heartbeat text-danger"></i>
                                                <span class="text-muted">Voir dossier</span>
                                            </div>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${file.status == 'EN_ATTENTE'}">
                                                    <span class="badge-waiting">
                                                        <i class="fas fa-hourglass-half"></i> En Attente
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge-taken">
                                                        <i class="fas fa-check"></i> Pris en Charge
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${file.status == 'EN_ATTENTE'}">
                                                <form method="POST" style="display:inline;">
                                                    <input type="hidden" name="action" value="take-charge"/>
                                                    <input type="hidden" name="fileAttenteId" value="${file.id}"/>
                                                    <input type="hidden" name="patientId" value="${file.patient.id}"/>
                                                    <button type="submit" class="btn btn-sm btn-success" title="Prendre en charge">
                                                        <i class="fas fa-hand-holding-medical"></i> Prendre en charge
                                                    </button>
                                                </form>
                                            </c:if>
                                            <a href="${pageContext.request.contextPath}/generaliste/consultation?action=new&patientId=${file.patient.id}"
                                               class="btn btn-sm btn-primary" title="Créer consultation">
                                                <i class="fas fa-file-medical"></i>
                                            </a>
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
                        <h5 class="text-muted">Aucun patient dans la file d'attente</h5>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
