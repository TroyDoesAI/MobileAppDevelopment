/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// Importing necessary React functions and React Native components
import React, { useState } from 'react';
import { View, TextInput, Text, Button, StyleSheet } from 'react-native';

// Importing the PostfixCalculator logic for evaluating expressions
import PostfixCalculator from '../Logic/PostfixCalculator';

// Defining the main PostfixCalculatorView component
const PostfixCalculatorView = () => {

  // Creating state variables for the expression input and its result
  const [expression, setExpression] = useState('');
  const [result, setResult] = useState('');

  // Defining a function to evaluate the given expression
  const evaluateExpression = () => {
    try {
      // Attempt to evaluate the expression using the imported PostfixCalculator
      const evaluation = PostfixCalculator.evaluate(expression);
      // If successful, set the result to the evaluated value
      setResult(String(evaluation));
    } catch (error) {
      // If there's an error in evaluation, display the error message
      setResult(`Error: ${error.message}`);
    }
  };

  // Defining a function to clear both the expression and result fields
  const clearFields = () => {
    setExpression('');
    setResult('');
  };

  // Rendering the main component UI
  return (
    <View style={styles.container}>
      // Header section containing the calculator's title
      <View style={styles.header}>
        <Text style={styles.title}>Postfix Calculator</Text>
      </View>

      // Input field for users to type in their postfix expression
      <TextInput
        style={styles.input}
        value={expression}
        onChangeText={setExpression} // Function to update the expression state variable
        placeholder="Expression"
        accessibilityLabel="expression"  // Label for testing purposes
      />

      // Display field for the calculated result (or error message)
      <TextInput
        style={styles.input}
        value={result}
        editable={false} // This input field is read-only
        placeholder="Result"
        accessibilityLabel="result" // Label for testing purposes
      />

      // Container for buttons
      <View style={styles.buttonContainer}>
        // "Evaluate" button to trigger the expression evaluation
        <Button
            title="evaluate"
            onPress={evaluateExpression}
            color="#007AFF"
            accessibilityLabel="evaluate" // Label for testing purposes
        />
        // "Clear" button to reset both expression and result fields
        <Button
            title="clear"
            onPress={clearFields}
            color="#FF3B30"
            accessibilityLabel="clear" // Label for testing purposes
        />
      </View>
    </View>
  );
};

// Styling for the components using React Native's StyleSheet
const styles = StyleSheet.create({
  container: {
    padding: 20, // Outer padding for the main container
  },
  header: {
    flexDirection: 'row', // Layout children in a row
    alignItems: 'center', // Center children vertically
    marginBottom: 20, // Margin at the bottom
  },
  title: {
    fontSize: 24, // Font size for the title
    marginLeft: 10, // Left margin to give some spacing
  },
  input: {
    borderWidth: 1, // Width of the border around input
    borderColor: '#ccc', // Color of the border
    borderRadius: 5, // Rounded corners
    padding: 10, // Inner padding
    marginBottom: 10, // Bottom margin
  },
  buttonContainer: {
    flexDirection: 'row', // Layout buttons in a row
    justifyContent: 'space-between', // Distribute buttons evenly
    marginTop: 20, // Margin at the top
  },
});

// Exporting the component to be used in other parts of the application
export default PostfixCalculatorView;
