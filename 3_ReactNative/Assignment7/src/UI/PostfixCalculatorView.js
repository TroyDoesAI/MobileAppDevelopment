/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

//import React from 'react';
//import { View } from 'react-native';
//
//import PostfixCalculator from '../Logic/PostfixCalculator';
//
//const PostfixCalculatorView = () => {
//  return (
//    <View>
//      {/* Your JSX goes here */}
//    </View>
//  );
//};
//
//export default PostfixCalculatorView;

import React, { useState } from 'react';
import { View, TextInput, Text, Button, StyleSheet } from 'react-native';

import PostfixCalculator from '../Logic/PostfixCalculator';

const PostfixCalculatorView = () => {
  const [expression, setExpression] = useState('');
  const [result, setResult] = useState('');

  const evaluateExpression = () => {
    try {
      const evaluation = PostfixCalculator.evaluate(expression);
      setResult(String(evaluation));
    } catch (e) {
      setResult(e);  // Set the error message directly without transforming it
    }
  };

  const clearFields = () => {
    setExpression('');
    setResult('');
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Postfix Calculator</Text>
      </View>
      <TextInput
        style={styles.input}
        value={expression}
        onChangeText={setExpression}
        placeholder="expression"
      />
      <TextInput
        style={styles.input}
        value={result}
        editable={false}
        placeholder="result"
      />
      <View style={styles.buttonContainer}>
        <Button title="evaluate" onPress={evaluateExpression} color="#007AFF" />
        <Button title="clear" onPress={clearFields} color="#FF3B30" />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 20,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 20,
  },
  title: {
    fontSize: 24,
    marginLeft: 10,
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 5,
    padding: 10,
    marginBottom: 10,
  },
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 20,
  },
});

export default PostfixCalculatorView;