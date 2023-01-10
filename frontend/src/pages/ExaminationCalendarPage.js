import React from 'react';
import StandardLayout from './../layouts/StandardLayout';
import {faClinicMedical, faNotesMedical, faUserAlt, faUserFriends} from "@fortawesome/free-solid-svg-icons";
import ExaminationCalendar from "../components/ExaminationCalendar/ExaminationCalendar";

const examinationCalendarPage = props => {
    const navLinks = [
        { url: "/clinics", icon: faClinicMedical, text: "Clinics" },
        { url: "/predefinedExaminations", icon: faNotesMedical, text: "Available examinations" }
      ];
    
    
    return (
        <StandardLayout navLinks={navLinks}>
            <div style={{width: "85%", margin: "0px auto"}}>
                <ExaminationCalendar />
            </div>
        </StandardLayout>
    );
}

export default examinationCalendarPage;