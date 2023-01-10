import React from 'react';
import classes from './Recipe.module.css';
import {Col, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUserNurse, faUser, faHistory, faPrescriptionBottle} from '@fortawesome/free-solid-svg-icons';

const recipe = props => {
    const medications = props.medication.map((medic, index) => (
        <>
            <FontAwesomeIcon key={index} icon={faPrescriptionBottle} size="1x"/><span> {medic.name}</span><br/>
        </>
    ))
    
    return (
        <Row className={classes.oneRecipe}>
            <Col lg={4} md={4} sm={12} xs={12} className="my-auto">
                <FontAwesomeIcon icon={faUserNurse} size="1x"/><span> {["Dr.", props.doctor.firstName, props.doctor.lastName].join(' ')}</span><br/>
                <FontAwesomeIcon icon={faUser} size="1x"/><span> {[props.patient.firstName, props.patient.lastName].join(' ')}</span><br/>
                <FontAwesomeIcon icon={faHistory} size="1x"/><span> {props.timeCreated.toString()}</span><br/>
            </Col>
            <Col lg={4} md={4} sm={12} xs={12} className="my-auto">
                {medications}
            </Col>
            <Col lg={4} md={4} sm={12} xs={12} className="my-auto">
                <button className="btn btn-success mx-1" onClick={() => props.onApprove(props.id)}>Approve</button>
                <button className="btn btn-danger" onClick={() => props.onReject(props.id)}>Reject</button>
            </Col>
        </Row>
    );
}

export default recipe;