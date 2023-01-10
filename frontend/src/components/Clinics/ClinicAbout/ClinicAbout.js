import axios from 'axios';
import React from 'react';
import ReactStars from "react-rating-stars-component";
import { withRouter } from 'react-router';
import classes from '../CreateUpdateClinic/CreateUpdateClinic.module.css'

class ClinicAbout extends React.Component{

    state={
        id: null,
        name: "",
        address: "",
        description: "",
        rating: 0,
        priceList: [],

    }
    

    componentDidMount(){
        axios
        .get("/clinics/" + this.props.match.params.id)
        .then(response =>{
            this.setState({
                id: response.data.id,
                name: response.data.name,
                address: response.data.address,
                description: response.data.description,
                rating: response.data.rating,
                priceList: response.data.priceList.map(item=>({...item}))
            })
        })
    }



    render(){
        

        let priceList = <h6>The clinic does not have a price list.</h6>;
        if(this.state.priceList.length > 0){
            priceList = this.state.priceList.map(priceItem => 
                <div className={classes.pricelist}>
                    <div className={classes.pricelistHeading}>
                        <h6>{priceItem.name}</h6>
                        <h6>{priceItem.price} rsd</h6>
                    </div>
                    <p>{priceItem.description}</p>
                </div>
                )
        }

        let stars = {
            size: 30,
            value: this.state.rating,
            edit: false,
            isHalf: true
        }
        
        return(
            <>
            <div style={{marginTop: "5vh"}}>
            <h1>{this.state.name}</h1>
            <span>Address: </span><span><strong>{this.state.address}</strong></span>
            <br></br>
            <span>Rating: </span><ReactStars {...stars}/> 
            <br></br>
            <span>Description:</span>
            <br></br>
            <span><i style={{fontSize:"1.25rem"}}>{this.state.description}</i></span>
            </div>
            <hr></hr>
            <h2 style={{textAlign:'center', marginTop:'3vh'}}>Price list</h2>
            {priceList}
            {/* <hr></hr>
            <h2 style={{textAlign:'center', marginTop:'3vh'}}>Available examinations</h2> */}
            </>
        );

    }

}

export default withRouter(ClinicAbout);