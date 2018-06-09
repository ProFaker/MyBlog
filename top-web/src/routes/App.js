import React from 'react';
import { connect } from 'dva';
import styles from './App.css';
import MainLayout from '../components/MainLayout/MainLayout';

function App({children}) {
  return (
    <MainLayout location={location}>
        {
          children
        }
    </MainLayout>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(App);
