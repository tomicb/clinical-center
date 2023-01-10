import React from 'react';
import {Col, Row} from "react-bootstrap";
import classes from './Clinic.module.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAddressCard, faClinicMedical, faEdit, faCalendarAlt, faUserNurse, faUserMd} from "@fortawesome/free-solid-svg-icons";
import ReactStars from "react-rating-stars-component";
import {Link} from "react-router-dom";
import authService from "../../../services/auth-service";

const clinic = props => {
    
    const stars = {
        size: 30,
        value: props.rating,
        edit: false,
        isHalf: true
    };
    
    return (
        <Row className={classes.clinic}>
            <Col lg={4} md={4} sm={12} xs={12} className="my-auto">
                <Link to={`/clinics/about/${props.id}`}
                className={classes.textDecoration}>
                    <FontAwesomeIcon icon={faClinicMedical} size="1x"/>
                    <span> {props.name}</span>
                    <br/>
                </Link>
            </Col>
            <Col lg={2} md={2} sm={12} xs={12} className="m-auto">
                <span><ReactStars {...stars}/></span>
            </Col>
            <Col lg={4} md={4} sm={12} xs={12} className="my-auto">
                <FontAwesomeIcon icon={faAddressCard} size="1x"/>
                <span> {props.address}</span>
            </Col>
            <Col lg={2} md={2} sm={12} xs={12} className="my-auto">
                <Row>
                    <Link
                        to={`/clinics/${props.id}/doctors`}
                        className={classes.textDecoration}>
                        <FontAwesomeIcon icon={faUserMd} style={{fontSize: "1.4rem"}} className="ml-2"/>
                    </Link>
                    <Link
                        to={`/clinics/${props.id}/nurses`}
                        className={classes.textDecoration}>
                        <FontAwesomeIcon icon={faUserNurse} style={{fontSize: "1.4rem"}} className="ml-2"/>
                    </Link>
                    <Link
                        to={`/examination/available/${props.id}`}
                        className={classes.textDecoration}>
                        <FontAwesomeIcon icon={faCalendarAlt} style={{fontSize: "1.4rem"}} className="ml-2"/>
                    </Link>
                    {authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR" || authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR" ?<Link
                        to={`/clinics/update/${props.id}`}
                        className={classes.textDecoration}>
                        <FontAwesomeIcon icon={faEdit} style={{fontSize: "1.4rem"}} className="ml-2"/>
                    </Link> : null}
                </Row>
            </Col>
        </Row>
    );
    
}

export default clinic;