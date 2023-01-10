import React from 'react';
import StandardLayout from './../layouts/StandardLayout';
import {Container} from 'react-bootstrap';
import {faCalendarAlt, faCapsules, faUserAlt, faUserFriends} from "@fortawesome/free-solid-svg-icons";
import Recipes from './../components/Recipes/Recipes';

const recipeCertificationPage = props => {
    const navLinks = [
        {url:'/patients', icon:faUserFriends, text:'Patients'},
        {url:'/work-calendar', icon:faCalendarAlt, text:'Work calendar'},
        {url:'/recipes', icon:faCapsules, text:'Recipes'},
        {url:'/profile', icon:faUserAlt, text:'Profile'}];
    
    return (
        <StandardLayout navLinks={navLinks}>
            <Container>
                <Recipes />
            </Container>
        </StandardLayout>
    );
}

export default recipeCertificationPage;