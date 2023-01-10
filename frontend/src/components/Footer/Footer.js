import React from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import classes from './Footer.module.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFacebook, faTwitter, faInstagram, faLinkedin} from "@fortawesome/free-brands-svg-icons";
import {faPhoneAlt, faEnvelope} from "@fortawesome/free-solid-svg-icons";

const footer = () => (
    <footer>
        <Container className={classes.upperFooter}>
            <Row>
                <Col xs={12} md={4} className={classes.footerContent}>
                    <h4>Locations</h4>
                    <p className="my-0">Bulevar Kralja Petra 42, Novi Sad</p>
                    <p className="my-0">Bulevar Mihajla Pupina 10, Beograd</p>
                </Col>
                <Col xs={12} md={4} className={classes.footerContent}>
                    <h4>Social media</h4>
                    <a href="https://www.facebook.com/">
                        <FontAwesomeIcon icon={faFacebook} size='1x' className={classes.socialIcons} />
                    </a>
                    <a href="https://twitter.com/?lang=en">
                        <FontAwesomeIcon icon={faTwitter} size='1x' className={classes.socialIcons} />
                    </a>
                    <a href="https://www.instagram.com/">
                        <FontAwesomeIcon icon={faInstagram} size='1x' className={classes.socialIcons} />
                    </a>
                    <a href="https://rs.linkedin.com/">
                        <FontAwesomeIcon icon={faLinkedin} size='1x' className={classes.socialIcons} />
                    </a>
                </Col>
                <Col xs={12} md={4} className={classes.footerContent}>
                    <h4>Information</h4>
                    <div className={classes.footerInfo}>
                        <a href="tel:+38162551827">
                            <FontAwesomeIcon icon={faPhoneAlt} size='1x' />
                            <span>+38162551827</span>
                        </a>
                        <a href="mailto:info@cliniccentre.rs">
                            <FontAwesomeIcon icon={faEnvelope} size='1x' />
                            <span>info@cliniccentre.rs</span>
                        </a>
                    </div>
                </Col>
            </Row>
        </Container>
        <hr />
        <div className={classes.lowerFooter}>
            <p>2021 &copy; Copyrights Clinic Cetre</p>
        </div>
    </footer>
);

export default footer;