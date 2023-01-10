import React from "react";
import authSerice from "./auth-service";
import { uniqBy } from 'lodash';
import {Redirect} from "react-router-dom";
import PredefinedExaminationPage from "../pages/PredefinedExaminationPage";
import RecipeCertificationPage from "../pages/RecipeCertificationPage";
import PatientsSearchPage from "../pages/PatientsSearchPage";
import CreateUpdatePatientPage from "../pages/CreateUpdatePatientPage";
import CreateUpdateNursePage from "../pages/CreateUpdateNursePage";
import MedicalRecordPage from "../pages/MedicalRecordPage";
import ExaminationPage from "../pages/ExaminationPage";
import WorkCalendarPage from "../pages/WorkCalendarPage";
import ClinicsSearchPage from "../pages/ClinicsSearchPage";
import ClinicReportPage from "../pages/ClinicReportPage";
import AuthorizationPage from "../pages/AuthorizationPage";
import ConfirmPasswordlessLoginPage from "../pages/ConfirmPasswordlessLoginPage";
import PasswordUpdatePage from "../pages/PasswordUpdatePage";
import RegistrationRequestPage from "../pages/RegistrationRequestPage"
import CreateUpdateDoctorsPage from "../pages/CreateUpdateDoctorsPage";
import EvaluateDoctorPage from "../pages/EvaluateDoctorPage";
import CreateUpdateAdministratorPage from "../pages/CreateUpdateClinicAdministratorPage";
import CreateUpdateClinicsPage from "../pages/CreateUpdateClinicsPage";
import AdministratorsPage from "../pages/ClinicAdministratorsPage";
import ExaminationCalendarPage from "../pages/ExaminationCalendarPage";
import ClinicAboutPage from "../pages/ClinicAboutPage";
import EvaluateClinicPage from "../pages/EvaluateClinicPage";
import DoctorsPage from "../pages/DoctorsPage";
import NursesPage from "../pages/NursesPage";
import UpdateProfilePage from "../pages/UpdateProfilePage";


class RouteService {
    authorizationRoutes = [
        { path: "/login", render: <AuthorizationPage login/>},
        { path: "/login/confirm", component: ConfirmPasswordlessLoginPage},
        { path: "/registration", component: AuthorizationPage},
    ]
    
    firstLoginRoutes = [
        { path: "/password-update", component: PasswordUpdatePage}
    ]
    // nekorisceni id-evi: 0, 3, 4, i obrisati doktoru id: 9
    routes = [
        { id: 0, path: "/predefinedExaminations", component: PredefinedExaminationPage},
        { id: 1, path: "/recipes", component: RecipeCertificationPage},
        { id: 2, path: "/patients", component: PatientsSearchPage},
        { id: 3, path: "/patients/create", render:  <CreateUpdatePatientPage creating/>},
        { id: 4, path: "/patients/update/:jmbg", render: <CreateUpdatePatientPage creating={false}/>},
        { id: 5, path: "/nurses/create", render: <CreateUpdateNursePage creating/>},
        { id: 6, path: "/nurses/update/:jmbg", render: <CreateUpdateNursePage creating={false}/>},
        { id: 7, path: "/clinic-reports", component: ClinicReportPage},
        { id: 8, path: "/clinics", component: ClinicsSearchPage},
        { id: 9, path: "/work-calendar", component: WorkCalendarPage},
        { id: 10, path: "/work-calendar/:jmbg", component: WorkCalendarPage},
        { id: 11, path: "/examinations/:id", component: ExaminationPage},
        { id: 12, path: "/medical-records/:jmbg", component: MedicalRecordPage},
        { id: 13, path: "/users/requests", component : RegistrationRequestPage},
        { id: 14, path: "/doctors/create", render:  <CreateUpdateDoctorsPage creating/>},
        { id: 15, path: "/doctors/update/:jmbg", render: <CreateUpdateDoctorsPage creating={false}/>},
        { id: 16, path: "/doctors/evaluate/:jmbg", render: <EvaluateDoctorPage/>},
        { id: 17, path: "/clinic-administrators/create", render: <CreateUpdateAdministratorPage creating clinicAdmin/>},
        { id: 18, path: "/clinic-administrators/update/:id", render: <CreateUpdateAdministratorPage creating={false} clinicAdmin/>},
        { id: 19, path: "/clinic-centre-administrators/create", render: <CreateUpdateAdministratorPage creating clinicAdmin={false}/>},
        { id: 20, path: "/clinic-centre-administrators/update/:id", render: <CreateUpdateAdministratorPage creating={false} clinicAdmin={false}/>},
        { id: 21, path: "/clinics/create", render: <CreateUpdateClinicsPage creating/>},
        { id: 22, path: "/clinics/update/:id", render: <CreateUpdateClinicsPage creating={false}/>},
        { id: 23, path: "/clinics/:id/administrators", component: AdministratorsPage},
        { id: 24, path: "/examination/available/:id", component: ExaminationCalendarPage},
        { id: 25, path: "/clinics/about/:id", component: ClinicAboutPage},
        { id: 26, path: "/clinics/evaluate/:id", render: <EvaluateClinicPage/>},
        { id: 27, path: "/password-update", component: PasswordUpdatePage},
        { id: 28, path: "/clinics/:id/doctors", component: DoctorsPage},
        { id: 29, path: "/clinics/:id/nurses", component: NursesPage},
        { id: 30, path: "/doctors", component: DoctorsPage},
        { id: 31, path: "/nurses", component: NursesPage},
        { id: 32, path: "/my-profile", render: <UpdateProfilePage creating={false}/>},
        { id: 33, path: "/my-clinic", render: <CreateUpdateClinicsPage creating={false}/>},
        { id: 34, path: "/my-medical-record", component: MedicalRecordPage},
        { id: 35, path: "/clinic-centre-administrators", render: <AdministratorsPage cca/>}

    ]
    
    redirect = [
        { role: "ROLE_DOCTOR", component: <Redirect to='/work-calendar'/> },
        { role: "ROLE_NURSE", component: <Redirect to='/recipes'/> },
        { role: "ROLE_PATIENT", component: <Redirect to='/clinics'/> },
        { role: "ROLE_CLINIC_ADMINISTRATOR", component: <Redirect to='/clinic-reports'/> },
        { role: "ROLE_CLINIC_CENTRE_ADMINISTRATOR", component: <Redirect to='/users/requests'/> },
        { role: "ROLE_FIRST_LOGIN", component: <Redirect to='/password-update'/> }
    ]
    
    roleClinicCentreAdministrator = [2,3,4,8,10,13,17,18,19,20,21,22,23,24,25,27,28,29,30,31,32,35];
    roleClinicAdministrator = [5,6,7,9,10,14,15,22,23,27,28,29,32,33];
    roleDoctor = [2,10,11,12,9,27,32];
    roleNurse = [1,2,27,32];
    rolePatient = [8,10,11,12,16,24,25,26,27,28,29,32,34];
    
    getAllowedRoutes = () => {
        // TAKE ROLES FROM TOKEN
    
        if(authSerice.getCurrentRole() === "ROLE_FIRST_LOGIN")
            return this.firstLoginRoutes
        
        let roles = authSerice.getRolesFromJwt();
        if(roles == null) {
            return this.authorizationRoutes
        }
        // MAKE A LIST OF ALLOWED ROUTES
        let allowedRoutes = this.getRouteListFromRoles(roles);
        // REMOVE DUPLICATES FROM LIST
        // RETURN LIST
        return  uniqBy(allowedRoutes, 'id');
    }
    
    getRedirect = () => {
        let currentRole = authSerice.getCurrentRole();
        if(currentRole){
            let route = this.redirect.find(red => red.role === currentRole);
            return route.component;
        }

        return  <Redirect to='/login'/>
    }
    
    getRouteListFromRoles = roles => {
        let ids = [];
        if(roles.includes("ROLE_DOCTOR"))
            ids.push(...this.roleDoctor);
        if(roles.includes("ROLE_NURSE"))
            ids.push(...this.roleNurse);
        if(roles.includes("ROLE_PATIENT"))
            ids.push(...this.rolePatient);
        if(roles.includes("ROLE_CLINIC_ADMINISTRATOR"))
            ids.push(...this.roleClinicAdministrator);
        if(roles.includes("ROLE_CLINIC_CENTRE_ADMINISTRATOR"))
            ids.push(...this.roleClinicCentreAdministrator);
        return ids.map(id => this.routes.find(route => route.id === id))
    }
}

export default new RouteService();