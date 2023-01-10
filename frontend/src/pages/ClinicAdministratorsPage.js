import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import { Container } from "react-bootstrap";
import {faClinicMedical} from "@fortawesome/free-solid-svg-icons";
import ClinicAdministrators from '../components/Administrators/Administrators'



const clinicAdministratorsPage = props => {
    const navLinks = [
        { url: "/clinics", icon: faClinicMedical, text: "Clinics" }
    ];
    
    return (
        <StandardLayout navLinks={navLinks}>
            <Container>
                <ClinicAdministrators isCca={props.cca}/>
            </Container>
        </StandardLayout>
    );
};

export default clinicAdministratorsPage;