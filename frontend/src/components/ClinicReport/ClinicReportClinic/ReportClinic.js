import React from 'react';
import {Card} from "react-bootstrap";
import clinicImg from '../../../assets/images/clinic.jpg';
import ReactStars from "react-rating-stars-component";

const reportClinic = props =>{

    const stars = {
        size: 30,
        value: props.rate,
        edit: false,
        isHalf: true
      };

    return(

    <Card style={{ width: '18rem' }} key={props.key}>
        <Card.Img variant="top" style={{background: 'white'}} src={clinicImg} />
        <Card.Body>
            <Card.Title>{props.name}</Card.Title>
            <Card.Text>{props.adress}</Card.Text>
            <Card.Title style={{textAlign: 'center'}}>
                <ReactStars {...stars}/>
            </Card.Title>
            
        </Card.Body>
    </Card>

    );

};

export default reportClinic;