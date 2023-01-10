import React from 'react';
import classes from './ExaminationInfo.module.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faClock, faCalendarAlt, faMoneyBillWave, faTags, faInfo} from '@fortawesome/free-solid-svg-icons';
import moment from "moment";

const ExaminationInfo = props => {
    
    let content = null;
    if(props.discount === 0) {
        content = (
            <div className={["text-center", classes.examinationCardInfo].join(" ")}>
                <h3>Time and price</h3>
                <FontAwesomeIcon icon={faClock} size="1x"/><p>{moment(props.start).format('HH:mm')} - {moment(props.end).format('HH:mm')}</p><br/>
                <FontAwesomeIcon icon={faCalendarAlt} size="1x"/><p>{moment(props.start).format('DD. MM. YYYY.')}</p><br/>
                <FontAwesomeIcon icon={faMoneyBillWave} size="1x"/><p>{props.price} RSD</p>
            </div>
        );
    } else {
        const discountPrice = props.price - (props.price * (props.discount / 100.0));
        content = (
            <div className="d-flex justify-content-around w-100">
                <div className={classes.examinationCardInfo}>
                    <h3>Time</h3>
                    <FontAwesomeIcon icon={faClock} size="1x"/><p>{moment(props.start).format('HH:mm')} - {moment(props.end).format('HH:mm')}</p><br/>
                    <FontAwesomeIcon icon={faCalendarAlt} size="1x"/><p>{moment(props.start).format('DD. MM. YYYY.')}</p><br/>
                </div>
                <div className={classes.examinationCardInfo}>
                    <h3>Price</h3>
                    <div className={classes.discount}>
                        <FontAwesomeIcon icon={faMoneyBillWave} size="1x"/><p className={classes.discountPrice}>{props.price} RSD</p><br/>
                        <FontAwesomeIcon icon={faTags} size="1x"/><p>{props.discount}%</p><br/>
                    </div>
                    <FontAwesomeIcon icon={faMoneyBillWave} size="1x"/><p>{discountPrice} RSD</p>
                </div>
            </div>
        );
    }
    
    return (
        <div className={classes.examination}>
            <h2>Information</h2>
            <div className={classes.examinationCard}>
                <FontAwesomeIcon icon={faInfo} size="8x" className={classes.mainIcon}/>
                {content}
            </div>
        </div>
    );
}

export default ExaminationInfo;