import React, { Component } from 'react';
import { AppRegistry } from 'react-native';
import { Button, View } from 'react-native';
import { NativeModules } from 'react-native';
const { ToastModule } = NativeModules;
export default class App extends Component {
  _showToast() {
    ToastModule.showToast('This is a native toast!!');
  }

render() {
  return (
    <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
      <Button onPress={this._showToast} title="Toast Btn" />
    </View>
  );
}
}