import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import {Container} from "react-bootstrap";
import {faCalendarAlt, faCapsules, faUserAlt, faUserFriends} from "@fortawesome/free-solid-svg-icons";
import CreateUpdateAdministrator from "../components/Administrators/CreateUpdateAdministrator/CreateUpdateAdministrator";

const CreateUpdateClinicAdministratorPage = props => {
    const navLinks = [
        {url: "/patients", icon: faUserFriends, text: "Patients"},
        {url: "/work-calendar", icon: faCalendarAlt, text: "Work calendar"},
        {url: "/recipes", icon: faCapsules, text: "Recipes"},
        {url: "/profile", icon: faUserAlt, text: "Profile"},
    ];
    
    return (
        <StandardLayout navLinks={navLinks}>
            <Container>
                <CreateUpdateAdministrator creating={props.creating} clinicAdmin={props.clinicAdmin}/>
            </Container>
        </StandardLayout>
    );
};

export default CreateUpdateClinicAdministratorPage;
