import React, { Component } from "react";
import Recipe from './Recipe/Recipe';
import classes from './Recipes.module.css';
import {Col, Row} from "react-bootstrap";
import axios from "axios";

class Recipes extends Component {
    
    state = {
        recipes: []
    };
    
    componentDidMount() {
        axios.get('/recipes')
            .then(response => {
                this.setState({recipes: response.data});
            })
            .catch(error => console.log(error));
    }
    
    approveRecipeHandler = (id) => {
        axios.get('/recipes/approve/' + id)
            .then(resonse => {
                if(resonse.status === 200 && resonse.data.status === "APPROVED") {
                    let recipes = [...this.state.recipes];
                    recipes = recipes.filter(rec => rec.id !== id);
                    this.setState({recipes: recipes});
                }
            })
            .catch(error => console.log(error));
    }
    
    rejectRecipeHandler = (id) => {
        axios.get('/recipes/reject/' + id)
            .then(resonse => {
                if(resonse.status === 200 && resonse.data.status === "REJECTED") {
                    let recipes = [...this.state.recipes];
                    recipes = recipes.filter(rec => rec.id !== id);
                    this.setState({recipes: recipes});
                }
            })
            .catch(error => console.log(error));
    }
    
    render() {
        
        let recipes = <h3>There are no recipes to certify!</h3>;
        let recipesHeading = null;
        if(this.state.recipes.length > 0) {
            recipes = this.state.recipes.map(recipe => (
                <Recipe key={recipe.id}
                    id={recipe.id}
                    doctor={recipe.doctor}
                    patient={recipe.patient}
                    timeCreated={recipe.end}
                    medication={recipe.medication}
                    onApprove={this.approveRecipeHandler}
                    onReject={this.rejectRecipeHandler}/>));
            
            recipesHeading = (
                <Row className={classes.recipesHeading}>
                    <Col lg={4} md={4} sm={12} xs={12}>
                        <h3>Information</h3>
                    </Col>
                    <Col lg={4} md={4} sm={12} xs={12}>
                        <h3>Medications</h3>
                    </Col>
                </Row>
            );
        }
        
        return (
            <>
                <h1 className={["text-center", "my-5", classes.recipesTitle].join(' ')}>Recipe certification</h1>
                {recipesHeading}
                <div className={["mb-5", classes.recipesContent].join(' ')}>
                    {recipes}
                </div>
            </>
        )
    }
}

export default Recipes;