import React from 'react';
import axios from "axios";
import ReportClinic from './ClinicReportClinic/ReportClinic';
import {Container} from 'react-bootstrap';
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";
import Carousel from 'react-elastic-carousel';
import classes from './ReportClinics.module.css'

// rgba(103, 58, 183, 0.1)


class ReportClinics extends React.Component{
    state={
        clinics:[],
        breakPoints: [
          { width: 1, itemsToShow: 1 },
          { width: 550, itemsToShow: 2, itemsToScroll: 2, pagination: false },
          { width: 850, itemsToShow: 3 },
          { width: 1150, itemsToShow: 4, itemsToScroll: 2 },
          { width: 1450, itemsToShow: 5 },
          { width: 1750, itemsToShow: 6 },
        ]
    }

    componentDidMount(){
        axios.get('/clinic-reports')
        .then(response =>{
            this.setState({clinics: response.data.clinics});
        })
        .catch(error => console.log(error))
    }

    render(){
      let transformedClinics = this.state.clinics
      .map(clinic =>{
        console.log(clinic)
        return(
            <ReportClinic
            adress={clinic.adress}
            rate={clinic.rating}
            name={clinic.name}
            key={clinic.id}/>
      )
      })

      return(
        <Container>
          <h2 style={{textAlign:'center', padding: '15px',color: '#9A1750'}}> Clinic centres </h2>
          <div className={classes.rec}>
          <Carousel  itemsToShow={3} itemsToScroll={3} breakPoints={this.state.breakpoints}>
            {transformedClinics}
          </Carousel>
          </div>
          <hr style={{color: '#5D001E'}}></hr>
        </Container>
      );
    };
}

export default ReportClinics;