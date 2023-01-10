import axios from 'axios';
import React from 'react';
import classes from '../Recipes/Recipes.module.css';
import {Col, Row} from "react-bootstrap";
import PredefinedExamination from './PredefinedExamination/PredefinedExamination'

class PredefinedExaminations extends React.Component {

    state = {
        examinations : []
    };

    componentDidMount(){
        axios
        .get('/examinations/predefined')
        .then(response=>{
            this.setState({examinations: response.data})
        })
        .catch(err=>{
            console.log(err);
        })
    }

    scheduleHandler = (id) =>{

        console.log("RADI" + id)

        axios
        .put('/examinations/predefined-schedule/' + id)
        .then(response => {

            let examinations = [...this.state.examinations];
            examinations = examinations.filter(exa => exa.id !== id);
            this.setState({examinations: examinations});
        })
        .catch(error => console.log(error))
    }

    render(){
        let examinations = <h3>There is no available examinations!</h3>
        let recipesHeading = null;
        if(this.state.examinations.length > 0){
            examinations = this.state.examinations.map(examination=>( 
                <PredefinedExamination
                key={examination.id}
                id={examination.id}
                doctor={examination.doctor}
                time={examination.start}
                price={examination.price}
                discount={examination.discount}
                title={examination.title}
                toSchedule={this.scheduleHandler}
                />
            
            ))

            recipesHeading = (
                <Row className={classes.recipesHeading}>
                    <Col lg={3} md={3} sm={12} xs={12}>
                        <h3>Information</h3>
                    </Col>
                    <Col lg={3} md={3} sm={12} xs={12}>
                        <h3>Examination</h3>
                    </Col>
                    <Col lg={3} md={3} sm={12} xs={12}>
                        <h3>Price</h3>
                    </Col>
                </Row>
            );
        }
        return(
            <>
            <h1 className={["text-center", "my-5", classes.recipesTitle].join(' ')}>Available examinations</h1>
            {recipesHeading}
            <div className={["mb-5", classes.recipesContent].join(' ')}>
                {examinations}
            </div>
            </>
        );
        
    }

}

export default PredefinedExaminations;