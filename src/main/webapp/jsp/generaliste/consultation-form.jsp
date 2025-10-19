<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Nouvelle Consultation - Medex</title>
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
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            padding: 30px;
            max-width: 800px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <h2 class="mb-4"><i class="fas fa-file-medical text-success"></i> Nouvelle Consultation</h2>

            <!-- Show error if any -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <i class="fas fa-exclamation-triangle"></i> ${error}
                </div>
            </c:if>

            <form method="POST" action="${pageContext.request.contextPath}/generaliste/consultation">

                <!-- Patient Selection -->
                <div class="mb-3">
                    <label for="patientId" class="form-label">
                        <i class="fas fa-user"></i> Patient *
                    </label>
                    <select class="form-select" id="patientId" name="patientId" required>
                        <option value="">-- Sélectionner un patient --</option>
                        <c:forEach items="${patients}" var="patient">
                            <option value="${patient.id}">
                                ${patient.prenom} ${patient.nom} - ${patient.numeroSecuriteSociale}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Motif -->
                <div class="mb-3">
                    <label for="motif" class="form-label">
                        <i class="fas fa-clipboard"></i> Motif de Consultation *
                    </label>
                    <textarea class="form-control" id="motif" name="motif" rows="3"
                              placeholder="Ex: Douleurs thoraciques, Fièvre..." required></textarea>
                </div>

                <!-- Observations -->
                <div class="mb-3">
                    <label for="observations" class="form-label">
                        <i class="fas fa-notes-medical"></i> Observations
                    </label>
                    <textarea class="form-control" id="observations" name="observations" rows="4"
                              placeholder="Vos observations cliniques..."></textarea>
                </div>

                <!-- Cost Display -->
                <div class="alert alert-success">
                    <strong><i class="fas fa-money-bill-wave"></i> Coût: 150 DH</strong> (fixe)
                </div>

                <!-- Buttons -->
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-success">
                        <i class="fas fa-save"></i> Créer Consultation
                    </button>
                    <a href="${pageContext.request.contextPath}/generaliste/consultation" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Retour
                    </a>
                </div>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

