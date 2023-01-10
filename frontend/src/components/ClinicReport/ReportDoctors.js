import React from 'react';
import axios from "axios";
import ReportDoctor from './ClinicReportDoctor/ReportDoctor';
import {Container} from 'react-bootstrap';
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";
import Carousel from 'react-elastic-carousel';
import classes from './ReportClinics.module.css'

// rgba(103, 58, 183, 0.1)


class ReportDoctors extends React.Component{
    state={
        doctors:[],
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
            this.setState({doctors: response.data.doctors});
        })
        .catch(error => console.log(error))
    }

    render(){
      let transformedDoctors = this.state.doctors
      .map(doctor =>{
        console.log(doctor)
        return(
            <ReportDoctor
            lastName={doctor.lastName}
            rate={doctor.rating}
            firstName={doctor.firstName}
            key={doctor.jmbg}
            email={doctor.email}/>
            
      )
      })

      return(
        <Container>
          <h2 style={{textAlign:'center', padding: '15px',color: '#9A1750'}}> Clinic doctors </h2>
          <div className={classes.rec}>
          <Carousel  itemsToShow={3} itemsToScroll={3} breakPoints={this.state.breakpoints}>
            {transformedDoctors}
          </Carousel>
          </div>
          <hr style={{color: '#5D001E'}}></hr>
        </Container>
      );
    };
}

export default ReportDoctors;