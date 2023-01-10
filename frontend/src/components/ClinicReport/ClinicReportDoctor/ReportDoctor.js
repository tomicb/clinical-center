import React from 'react';
import {Card} from "react-bootstrap";
import doctorImg from '../../../assets/images/doctorr.png';
import ReactStars from "react-rating-stars-component";

const reportDoctor = props =>{

    const stars = {
        size: 30,
        value: props.rate,
        edit: false,
        isHalf: true
      };

    return(

    <Card style={{ width: '18rem' }} key={props.key}>
        <Card.Img variant="top" style={{background: 'white'}} src={doctorImg} />
        <Card.Body>
            <Card.Title>{props.firstName} {props.lastName}</Card.Title>
            <Card.Text>{props.email}</Card.Text>
            <Card.Title style={{textAlign: 'center'}}>
                <ReactStars {...stars}/>
            </Card.Title>
            
        </Card.Body>
    </Card>

    );

};

export default reportDoctor;