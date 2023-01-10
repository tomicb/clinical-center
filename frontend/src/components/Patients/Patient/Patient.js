import React from 'react';
import {Col, Row} from "react-bootstrap";
import classes from './Patient.module.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {faEnvelope, faUser, faAddressCard, faNotesMedical, faEdit} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router-dom";
import authService from '../../../services/auth-service';

const patient = props =>{

    return (
        <Row className={classes.patient}>
            <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
                <FontAwesomeIcon icon={faUser} size="1x" />
                <span> {[props.firstName, props.lastName].join(" ")}</span>
            </Col>
            <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
                <FontAwesomeIcon icon={faEnvelope} size="1x" />
                <span> {props.email}</span>
            </Col>
            <Col lg={2} md={2} sm={12} xs={12} className="my-auto">
                <span> {props.jmbg}</span>
            </Col>
            <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
                <FontAwesomeIcon icon={faAddressCard} size="1x" />
                <span> {props.address}</span>
            </Col>
            <Col lg={1} md={1} sm={12} xs={12} className="my-auto">
                {authService.getCurrentRole() === "ROLE_DOCTOR" && <Link
                    to={`/medical-records/${props.jmbg}`}
                    key={patient.jmbg}
                    className={classes.textDecoration}>
                    <FontAwesomeIcon icon={faNotesMedical} style={{fontSize: "1.5rem"}}/>
                </Link>}
                {authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR" || authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR" ? <Link
                    to={`/patients/update/${props.jmbg}`}
                    key={patient.jmbg}
                    className={classes.textDecoration}>
                    <FontAwesomeIcon icon={faEdit} style={{fontSize: "1.5rem"}} className="ml-2"/>
                </Link> : null}
            </Col>
        </Row>
    );

}

export default patient;