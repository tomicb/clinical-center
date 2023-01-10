import React from 'react';
import classes from './AuthorizationLayout.module.css'
import Footer from './../components/Footer/Footer';
import {Col, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHandHoldingHeart} from '@fortawesome/free-solid-svg-icons';

const authorizationLayout = props => (
    <>
        <Row className={classes.row}>
            <Col lg={6} md={12}  className={["d-flex flex-column justify-content-center align-items-center", classes.welcomeSection].join(" ")}>
                <div className={["my-5", classes.welcome].join(" ")}>
                    <h1>Welcome to</h1>
                    <FontAwesomeIcon icon={faHandHoldingHeart} className={classes.welcomeIcon}/>
                    <h2>Clinic Centre</h2>
                </div>
            </Col>
            <Col lg={6} md={12} className="d-flex flex-column justify-content-center align-items-center my-5">
                {props.children}
            </Col>
        </Row>
        <Footer/>
    </>
);

export default authorizationLayout;