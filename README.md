# Clinical center system

#### This project was implemented by a team of 3 members.

### Course - Information security (2021)

#### Technologies used: JavaScript, React, Java, Spring Boot, Spring Security, JPA, MySQL

The clinical center system allows patients to schedule an appointment. Access to the system have nurses and doctors who can enter reports on performed examinations. The clinical center consists of several narrowly specialized clinics using the information system. The system keeps records of employees, registered clinics, patients, and their medical records.

##

### Functionalities I have implemented

#### Entering examination report data
The doctor starts the examination from his working calendar. During the examination, the doctor enters examination data and diagnosis. The doctor can additionally enter prescriptions that the nurse will verify. Examination data, diagnosis, and prescriptions are permanently recorded in the patient's medical record under the medical history.

#### Prescription approval
After examination, the nurse verifies the prescriptions doctor prescribed for the patient. The system has information on which nurse verified the prescription.

#### Work calendar
The doctor can view his examinations on a weekly, monthly, and yearly work calendar. The work calendar displays the time, duration, and patient's name for all doctor's examinations.

#### Passwordless login
The user can log in to the system without a password (passwordless login). After entering the email address, the system generated a token with a validity period of 10 minutes. The system sends a email containing token to the user's email address. The user has 10 minutes to open the email and visit the link. After the user clicks the link, the server will generate a new pair of refresh and access tokens for user authentication. The link can not be used more than once.

#### Token refresh
The access token has a validity period of 15 minutes. After the access token expires, the client sends a new request to refresh the access token. The system checks the identity of the user based on the refresh token. If such user exists and is not blocked, the server generates a new access token and sends it to the client. The newly generated token should replace the old one in the header of future HTTP requests. The access token can be refreshed until the refresh token expires.

#### Access control using the RBAC model
Access control is implemented with the help of roles and permissions. One user can have multiple roles, and one role can have multiple permissions.

#### Sensitive data encryption
Sensitive data is encrypted before saving in the database. When getting data from the database, the encrypted data must be decrypted with the help of the appropriate key.
