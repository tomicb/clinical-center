import React from 'react';
import {Col, Row} from "react-bootstrap";
import classes from '../../Recipes/Recipe/Recipe.module.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUserMd,  faHistory, faStethoscope, faMoneyBillWave, faPercent} from '@fortawesome/free-solid-svg-icons';

const predefinedExamination = props =>{


    return(

        <Row className={classes.oneRecipe}>
        <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
            <FontAwesomeIcon icon={faUserMd} size="1x"/><span> {["Dr.", props.doctor.firstName, props.doctor.lastName].join(' ')}</span><br/>
            <FontAwesomeIcon icon={faHistory} size="1x"/><span> {props.time.toString()}</span><br/>
        </Col>
        <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
            <FontAwesomeIcon icon={faStethoscope} size="1x"/><span> {props.title}</span><br/>
        </Col>
        <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
            <FontAwesomeIcon icon={faMoneyBillWave} size="1x"/><span> {props.price}</span><br/>
            <FontAwesomeIcon icon={faPercent} size="1x"/><span> {props.discount}</span><br/>
        </Col>
        <Col lg={3} md={3} sm={12} xs={12} className="my-auto">
            <button className="btn btn-success mx-1" onClick={() => props.toSchedule(props.id)}>Schedule</button>
        </Col>
    </Row>

    );
}

export default predefinedExamination;
