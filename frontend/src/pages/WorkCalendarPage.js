import React from 'react';
import StandardLayout from './../layouts/StandardLayout';
import {faCalendarAlt, faCapsules, faUserAlt, faUserFriends} from "@fortawesome/free-solid-svg-icons";
import WorkCalendar from "../components/WorkCalendar/WorkCalendar";

const workCalendarPage = props => {
    const navLinks = [
        {url: '/patients', icon:faUserFriends, text: 'Patients'},
        {url: '/work-calendar', icon:faCalendarAlt, text: 'Work calendar'},
        {url: '/recipes', icon:faCapsules, text: 'Recipes'},
        {url: '/profile', icon:faUserAlt, text: 'Profile'}];
    
    
    return (
        <StandardLayout navLinks={navLinks}>
            <div style={{width: "85%", margin: "0px auto"}}>
                <WorkCalendar />
            </div>
        </StandardLayout>
    );
}

export default workCalendarPage;