import React from 'react';
import classes from './Nurse.module.css';
import {Col, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUserNurse, faHome, faEdit, faUserPlus} from '@fortawesome/free-solid-svg-icons';
import {Link} from "react-router-dom";
import authService from '../../../services/auth-service';

const nurse = props => {
    
    return (
        <Row className={classes.admin}>
            <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
                <FontAwesomeIcon icon={faUserNurse} size="1x" className="mr-1"/>
                <span>{props.nurse.firstName} {props.nurse.lastName}</span>
            </Col>
            <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
                <span><strong>JMBG:</strong> {props.nurse.jmbg}</span>
            </Col>
            
            <Col lg={5} md={5} sm={12} xs={12} className="my-auto">
                <FontAwesomeIcon icon={faHome} size="1x" className="mr-1"/>
                <span>{props.nurse.address}</span>
            </Col>
            <Col lg={1} md={1} sm={12} xs={12} className="my-auto text-right">
                {(!props.choose && authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR") && <Link to={"/nurses/update/" + props.nurse.jmbg} className={classes.link}>
                    <FontAwesomeIcon icon={faEdit} style={{fontSize: "1.2rem", marginRight: "5px"}} />
                </Link>}
                {(props.choose && (authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR" || authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR")) && <FontAwesomeIcon icon={faUserPlus} className={classes.administratorBtn} onClick={() => props.btnClick(props.nurse.id)}/>}
            </Col>
        </Row>
    );
}

export default nurse;