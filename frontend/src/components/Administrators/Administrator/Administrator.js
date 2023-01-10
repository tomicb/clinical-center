import React from 'react';
import classes from './Administrator.module.css';
import {Col, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser, faUserPlus, faHome, faTimes, faEdit} from '@fortawesome/free-solid-svg-icons';
import {Link} from "react-router-dom";
import authService from "../../../services/auth-service";

const administrator = props => {
    
    return (
        <Row className={classes.admin}>
            <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
                <FontAwesomeIcon icon={faUser} size="1x" className="mr-1"/>
                <span>{props.admin.firstName} {props.admin.lastName}</span>
            </Col>
            <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
                <span><strong>JMBG:</strong> {props.admin.jmbg}</span>
            </Col>
            <Col lg={5} md={5} sm={12} xs={12} className="my-auto">
                <FontAwesomeIcon icon={faHome} size="1x" className="mr-1"/>
                <span>{props.admin.address}</span>
            </Col>
            <Col lg={1} md={1} sm={12} xs={12} className="my-auto text-right">
                {(props.isCca && authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR") && <Link to={"/clinic-centre-administrators/update/" + props.admin.id} className={classes.link}>
                    <FontAwesomeIcon icon={faEdit} style={{fontSize: "1.2rem", marginRight: "5px"}} />
                </Link>}
                {(!props.choose && !props.isCca && authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR") && <Link to={"/clinic-administrators/update/" + props.admin.id} className={classes.link}>
                    <FontAwesomeIcon icon={faEdit} style={{fontSize: "1.2rem", marginRight: "5px"}} />
                </Link>}
                {(props.choose && !props.isCca) && <FontAwesomeIcon icon={faUserPlus} className={classes.administratorBtn} onClick={() => props.btnClick(props.admin.id)}/>}
            </Col>
        </Row>
    );
}

export default administrator;