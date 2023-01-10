import React from "react"
import classes from '../components/Navbar/Navbar.module.css'
import authService from "./auth-service";
import {uniqBy} from "lodash";
import {Dropdown, DropdownButton} from "react-bootstrap";
import {faUserAlt ,faNotesMedical, faClinicMedical, faChartBar, faUsers, faCalendarAlt, faPrescriptionBottleAlt, faUserCheck, faUsersCog} from "@fortawesome/free-solid-svg-icons";

class NavbarService {
    
    links = [
        { id: 1, url: "/recipes", icon: faPrescriptionBottleAlt, text: "Recipes"},
        { id: 2, url: "/patients", icon: faUsers, text: "Patients"},
        { id: 3, url: "/clinics", icon: faClinicMedical, text: "Clinics"},
        { id: 4, url: "/clinic-reports", icon: faChartBar, text: "Reports"},
        { id: 5, url: "/work-calendar", icon: faCalendarAlt, text: "Calendar"},
        { id: 6, url: "/users/requests", icon: faUserCheck, text: "Registration requests"},
        { id: 7, url: "/my-medical-record", icon: faNotesMedical, text: "My medical record"},
        { id: 8, url: "/my-profile", icon: faUserAlt, text: "My profile"},
        { id: 9, url: "/my-clinic", icon: faClinicMedical, text: "My clinic"},
        { id: 10, url: "/clinic-centre-administrators", icon: faUsersCog, text: "Administrators"}
    ]
    
    roleClinicCentreAdministrator = [10,3,6,8];
    roleClinicAdministrator = [4,8,9];
    roleDoctor = [5,2,8];
    roleNurse = [1,2,8];
    rolePatient = [3,7,8];
    
    getAllowedNavbarLinks = () => {
        // TAKE ROLES FROM TOKEN
        let role = authService.getCurrentRole();
        // MAKE A LIST OF ALLOWED ROUTES
        let allowedNavbarLinks = this.getNavbarLinksFromCurrentRole(role);
        // REMOVE DUPLICATES FROM LIST
        // RETURN LIST
        return  uniqBy(allowedNavbarLinks, 'id');
    }
    
    getNavbarLinksFromCurrentRole = role => {
        let ids = [];
        if(role === "ROLE_DOCTOR")
            ids.push(...this.roleDoctor);
        else if(role === "ROLE_NURSE")
            ids.push(...this.roleNurse);
        else if(role === "ROLE_PATIENT")
            ids.push(...this.rolePatient);
        else if(role === "ROLE_CLINIC_ADMINISTRATOR")
            ids.push(...this.roleClinicAdministrator);
        else if(role === "ROLE_CLINIC_CENTRE_ADMINISTRATOR")
            ids.push(...this.roleClinicCentreAdministrator);
        return ids.map(id => this.links.find(link => link.id === id));
    }
    
    getRoleChangeDropdown = () => {
        let roles = authService.getRolesFromJwt();
        if(!roles || roles.length < 2)
            return null;
            
        let currentRole = authService.getCurrentRole();
        let buttons = roles.map(role => {
            if(role === "ROLE_DOCTOR" && role !== currentRole)
                return <Dropdown.Item onClick={() => {
                    authService.setCurrentRole("ROLE_DOCTOR");
                    //TODO REPLACE URL TO DOCTOR'S HOME PAGE
                    window.location.replace("/clinic/work-calendar");
                }}>Doctor</Dropdown.Item>;
            else if(role === "ROLE_NURSE" && role !== currentRole)
                return <Dropdown.Item onClick={() => {
                    authService.setCurrentRole("ROLE_NURSE");
                    //TODO REPLACE URL TO NURSE'S HOME PAGE
                    window.location.replace("/clinic/recipes");
                }}>Nurse</Dropdown.Item>;
            else if(role === "ROLE_PATIENT" && role !== currentRole)
                return <Dropdown.Item onClick={() => {
                    authService.setCurrentRole("ROLE_PATIENT");
                    //TODO REPLACE URL TO PATIENT'S HOME PAGE
                    window.location.replace("/clinic/clinics");
                }}>Patient</Dropdown.Item>;
            else if(role === "ROLE_CLINIC_ADMINISTRATOR" && role !== currentRole)
                return <Dropdown.Item onClick={() => {
                    authService.setCurrentRole("ROLE_CLINIC_ADMINISTRATOR");
                    //TODO REPLACE URL TO CA'S HOME PAGE
                    window.location.replace("/clinic/clinic-reports");
                }}>CA</Dropdown.Item>;
            else if(role === "ROLE_CLINIC_CENTRE_ADMINISTRATOR" && role !== currentRole)
                return <Dropdown.Item onClick={() => {
                    authService.setCurrentRole("ROLE_CLINIC_CENTRE_ADMINISTRATOR");
                    //TODO REPLACE URL TO CCA'S HOME PAGE
                    window.location.replace("/clinic/users/requests");
                }}>CCA</Dropdown.Item>;
        })
        
        return <DropdownButton id="dropdown-basic-button" title={this.getTitleFromRole(currentRole)} className={classes.navbarDropdown}>
                    {buttons}
              </DropdownButton>
    }
    
    getTitleFromRole = role => {
        if(role === "ROLE_DOCTOR")
            return "Doctor"
        else if(role === "ROLE_NURSE")
            return "Nurse"
        else if(role === "ROLE_PATIENT")
            return "Patient"
        else if(role === "ROLE_CLINIC_ADMINISTRATOR")
            return "CA"
        else if(role === "ROLE_CLINIC_CENTRE_ADMINISTRATOR")
            return "CCA"
    }
}

export default new NavbarService();