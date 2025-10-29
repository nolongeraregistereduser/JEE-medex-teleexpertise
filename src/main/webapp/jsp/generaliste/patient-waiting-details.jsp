<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient Details - Waiting List</title>
</head>
<body>
    <h1>Patient Details - Waiting List</h1>

    <a href="${pageContext.request.contextPath}/generaliste/file-attente">← Back to Waiting List</a>

    <hr>

    <h2>Patient Information</h2>
    <table border="1">
        <tr>
            <th>Name</th>
            <td>${patient.prenom} ${patient.nom}</td>
        </tr>
        <tr>
            <th>Date of Birth</th>
            <td>${patient.dateNaissance}</td>
        </tr>
        <tr>
            <th>Security Number</th>
            <td>${patient.numeroSecuriteSociale}</td>
        </tr>
        <tr>
            <th>Phone</th>
            <td>${patient.telephone != null ? patient.telephone : 'N/A'}</td>
        </tr>
        <tr>
            <th>Address</th>
            <td>${patient.adresse != null ? patient.adresse : 'N/A'}</td>
        </tr>
        <tr>
            <th>Insurance (Mutuelle)</th>
            <td>${patient.mutuelle != null ? patient.mutuelle : 'N/A'}</td>
        </tr>
    </table>

    <hr>

    <c:if test="${dossierMedical != null}">
        <h2>Medical Record</h2>
        <table border="1">
            <tr>
                <th>Medical History (Antecedents)</th>
                <td>${dossierMedical.antecedents != null ? dossierMedical.antecedents : 'None'}</td>
            </tr>
            <tr>
                <th>Allergies</th>
                <td>${dossierMedical.allergies != null ? dossierMedical.allergies : 'None'}</td>
            </tr>
            <tr>
                <th>Current Treatment</th>
                <td>${dossierMedical.traitementEnCours != null ? dossierMedical.traitementEnCours : 'None'}</td>
            </tr>
        </table>
    </c:if>

    <hr>

    <h2>Vital Signs (Latest 5 records)</h2>
    <c:choose>
        <c:when test="${not empty signesVitauxList}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Date/Time</th>
                        <th>Blood Pressure</th>
                        <th>Heart Rate</th>
                        <th>Temperature</th>
                        <th>Respiratory Rate</th>
                        <th>Weight</th>
                        <th>Height</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${signesVitauxList}" var="signes">
                        <tr>
                            <td>${signes.dateSaisie}</td>
                            <td>${signes.tension != null ? signes.tension : 'N/A'}</td>
                            <td>${signes.frequenceCardiaque != null ? signes.frequenceCardiaque : 'N/A'}</td>
                            <td>${signes.temperature != null ? signes.temperature : 'N/A'}°C</td>
                            <td>${signes.frequenceRespiratoire != null ? signes.frequenceRespiratoire : 'N/A'}</td>
                            <td>${signes.poids != null ? signes.poids : 'N/A'} kg</td>
                            <td>${signes.taille != null ? signes.taille : 'N/A'} cm</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No vital signs recorded.</p>
        </c:otherwise>
    </c:choose>

    <hr>

    <c:if test="${fileAttente != null}">
        <h2>Queue Information</h2>
        <p><strong>Arrival Time:</strong> ${fileAttente.dateArrivee}</p>
        <p><strong>Status:</strong> ${fileAttente.status}</p>
    </c:if>

    <hr>

    <h2>Actions</h2>
    <p>
        <a href="${pageContext.request.contextPath}/generaliste/request-specialist?patientId=${patient.id}">
            <button type="button">Request Specialist Opinion</button>
        </a>
    </p>
    <p>
        <a href="${pageContext.request.contextPath}/generaliste/consultation?action=new&patientId=${patient.id}">
            <button type="button">Create Consultation</button>
        </a>
    </p>

</body>
</html>
