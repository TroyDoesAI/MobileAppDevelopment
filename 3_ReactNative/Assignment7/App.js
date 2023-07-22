/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

/* **************************************************************************
 * DO NOT MODIFY THIS FILE
 * **************************************************************************/

import React from 'react';
import {SafeAreaView, StyleSheet} from 'react-native';

import PostfixCalculatorView from './src/UI/PostfixCalculatorView';
import CalendarGeneratorView from './src/UI/CalendarGeneratorView';

const App = () => {
  return (
    <SafeAreaView style={styles.root}>
      <PostfixCalculatorView />
      <CalendarGeneratorView />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  root: {
    flex: 1,
    padding: 10,
  },
});

export default App;
