import React from 'react';
import classes from './RegistrationRequest.module.css';
import {Col, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser, faHome} from '@fortawesome/free-solid-svg-icons';

const registrationRequest = props => (
    <Row className={classes.user}>
        <Col lg={3} md={5} sm={12} xs={12} className="my-auto">
            <FontAwesomeIcon icon={faUser} size="1x"/>
            <span> {props.fullName}</span>
        </Col>
        <Col lg={3} md={7} sm={12} xs={12} className="my-auto">
            <strong>JMBG: </strong>
            <span>{props.jmbg}</span>
        </Col>
        <Col lg={3} md={8} sm={12} xs={12} className="d-flex align-items-center">
            <FontAwesomeIcon icon={faHome} size="1x" className="mr-1"/>
            <span>{props.address}</span>
        </Col>
        <Col lg={3} md={4} sm={12} xs={12} className={classes.btns}>
            <button className="btn btn-md btn-success text-white mr-1" onClick={() => props.onApprove(props.id)}>Approve</button>
            <button className="btn btn-md btn-danger text-white" onClick={() => props.onReject(props.id)}>Reject</button>
        </Col>
    </Row>
)


export default registrationRequest;