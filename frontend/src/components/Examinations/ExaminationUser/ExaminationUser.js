import React from 'react';
import classes from './ExaminationUser.module.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUserNurse, faUserInjured, faHome, faHospital, faEnvelope} from '@fortawesome/free-solid-svg-icons';

const ExaminationUser = props => {
    
    let heading
    let addressIcon;
    let mainIcon;
    let preName = "";
    if(props.isDoctor) {
        preName = "Dr. "
        heading = <h2>Doctor</h2>
        addressIcon = <FontAwesomeIcon icon={faHospital} size="1x"/>
        mainIcon = <FontAwesomeIcon icon={faUserNurse} size="8x" className={classes.mainIcon}/>
    } else {
        heading = <h2>Patient</h2>
        addressIcon = <FontAwesomeIcon icon={faHome} size="1x"/>
        mainIcon = <FontAwesomeIcon icon={faUserInjured} size="8x" className={classes.mainIcon}/>
    }
    
    return (
        
        <div className={classes.person} style={props.medicalRecord ? {width: "40%"}: {}}>
            {heading}
            <div className={classes.personCard}>
                {mainIcon}
                <div className={classes.personCardInfo}>
                    <h3>{preName + props.user.firstName + " " + props.user.lastName}</h3>
                    <span className={classes.personCardInfoJmbg}>JMBG:</span><p>{props.user.jmbg}</p><br/>
                    {addressIcon}<p>{props.user.address}</p><br/>
                    <FontAwesomeIcon icon={faEnvelope} size="1x"/><p>{props.user.email}</p>
                </div>
            </div>
        </div>
    );
}

export default ExaminationUser;