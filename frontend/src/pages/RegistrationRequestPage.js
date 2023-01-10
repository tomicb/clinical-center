import React from 'react';
import StandardLayout from './../layouts/StandardLayout';
import {faCalendarAlt, faCapsules, faUserAlt, faUserFriends} from "@fortawesome/free-solid-svg-icons";
import RegistrationRequests from "../components/Registration/RegistrationRequests/RegistrationRequests";

const registrationRequestsPage = () => {
    const navLinks = [
        {url: '/patients', icon:faUserFriends, text: 'Patients'},
        {url: '/work-calendar', icon:faCalendarAlt, text: 'Work calendar'},
        {url: '/recipes', icon:faCapsules, text: 'Recipes'},
        {url: '/profile', icon:faUserAlt, text: 'Profile'}];
    
    
    return (
        <StandardLayout navLinks={navLinks}>
            <RegistrationRequests />
        </StandardLayout>
    );
}

export default registrationRequestsPage;