import React from 'react';
import navbarService from "../../services/navbar-service";
import authService from "../../services/auth-service";
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import classes from './Navbar.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHandHoldingHeart, faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import {Link} from "react-router-dom";

const navbar = () => {
    
    const navigationLinks = navbarService.getAllowedNavbarLinks().map((navLink, index) => (
        <Link key={index} to={navLink.url} className={classes.navLink}>
            <FontAwesomeIcon icon={navLink.icon} size='1x'/>{' '}
            {navLink.text}
        </Link>
    ));
    
    const dropdown = navbarService.getRoleChangeDropdown();
    
    return (
        <Navbar collapseOnSelect expand="lg" className={classes.nav}>
            <Container>
                <Link to="/" className={classes.navbarBrand}>
                    <FontAwesomeIcon icon={faHandHoldingHeart} size='1x'/>{' '}
                    Clinic Centre
                </Link>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" className={classes.navbarToggler}/>
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="ml-auto">
                        {navigationLinks}
                    </Nav>
                    <button className={classes.navLogoutBtn} onClick={authService.logout}>
                        <FontAwesomeIcon icon={faSignOutAlt} size='1x'/>{' '}
                        Logout
                    </button>
                    {authService.getCurrentRole() !== "ROLE_FIRST_LOGIN" && dropdown}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default navbar;