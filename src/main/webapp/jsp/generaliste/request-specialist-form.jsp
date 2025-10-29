<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Request Specialist - Medex</title>
</head>
<body>
    <h1>Request Specialist Opinion</h1>

    <a href="${pageContext.request.contextPath}/generaliste/patient-waiting-details?patientId=${patient.id}">← Back to Patient Details</a>

    <hr>

    <h2>Patient: ${patient.prenom} ${patient.nom}</h2>
    <p><strong>Security Number:</strong> ${patient.numeroSecuriteSociale}</p>

    <hr>

    <c:choose>
        <c:when test="${empty selectedSpecialty}">
            <!-- Step 1: Select Specialty -->
            <h2>Step 1: Select Medical Specialty</h2>
            <form method="GET">
                <input type="hidden" name="patientId" value="${patient.id}" />

                <p>
                    <label for="specialty">Choose Specialty:</label><br>
                    <select name="specialty" id="specialty" required>
                        <option value="">-- Select Specialty --</option>
                        <c:forEach items="${specialties}" var="spec">
                            <option value="${spec.name()}">${spec.displayName}</option>
                        </c:forEach>
                    </select>
                </p>

                <p>
                    <button type="submit">Next: Select Specialist</button>
                </p>
            </form>
        </c:when>

        <c:otherwise>
            <!-- Step 2: Select Specialist and Submit Request -->
            <h2>Step 2: Select Specialist and Submit Request</h2>

            <p><strong>Selected Specialty:</strong> ${selectedSpecialty}</p>

            <c:choose>
                <c:when test="${not empty specialists}">
                    <form method="POST">
                        <input type="hidden" name="patientId" value="${patient.id}" />

                        <h3>Available Specialists (sorted by price):</h3>
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Select</th>
                                    <th>Name</th>
                                    <th>Specialty</th>
                                    <th>Rate</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${specialists}" var="specialist">
                                    <tr>
                                        <td>
                                            <input type="radio" name="specialistId" value="${specialist.id}" required />
                                        </td>
                                        <td>${specialist.prenom} ${specialist.nom}</td>
                                        <td>${specialist.specialite}</td>
                                        <td>${specialist.tarif} DH</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <hr>

                        <h3>Request Details</h3>

                        <p>
                            <label for="motif">Consultation Reason (Motif):</label><br>
                            <input type="text" name="motif" id="motif" size="50" placeholder="Brief reason for consultation" />
                        </p>

                        <p>
                            <label for="question">Question for Specialist:</label><br>
                            <textarea name="question" id="question" rows="4" cols="50" placeholder="Describe the case and your question..."></textarea>
                        </p>

                        <p>
                            <label for="priorite">Priority Level:</label><br>
                            <select name="priorite" id="priorite">
                                <option value="NORMALE" selected>Normal</option>
                                <option value="URGENTE">Urgent</option>
                                <option value="NON_URGENTE">Non-Urgent</option>
                            </select>
                        </p>

                        <hr>

                        <p>
                            <button type="submit">Submit Specialist Request</button>
                            <a href="${pageContext.request.contextPath}/generaliste/patient-waiting-details?patientId=${patient.id}">
                                <button type="button">Cancel</button>
                            </a>
                        </p>
                    </form>
                </c:when>

                <c:otherwise>
                    <p style="color: red;">No specialists available for this specialty.</p>
                    <p>
                        <a href="${pageContext.request.contextPath}/generaliste/request-specialist?patientId=${patient.id}">
                            <button type="button">Choose Another Specialty</button>
                        </a>
                    </p>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

</body>
</html>

