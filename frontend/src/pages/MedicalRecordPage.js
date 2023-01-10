import React from 'react';
import StandardLayout from './../layouts/StandardLayout';
import {faCalendarAlt, faCapsules, faUserAlt, faUserFriends} from "@fortawesome/free-solid-svg-icons";
import MedicalRecord from "../components/MedicalRecord/MedicalRecord";
import {Container} from "react-bootstrap";

const medicalRecordPage = () => {
    const navLinks = [
        {url: '/patients', icon:faUserFriends, text: 'Patients'},
        {url: '/work-calendar', icon:faCalendarAlt, text: 'Work calendar'},
        {url: '/recipes', icon:faCapsules, text: 'Recipes'},
        {url: '/profile', icon:faUserAlt, text: 'Profile'}];
    
    
    return (
        <StandardLayout navLinks={navLinks}>
            <Container>
                <MedicalRecord />
            </Container>
        </StandardLayout>
    );
}

export default medicalRecordPage;