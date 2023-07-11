/*
#######################################################################
#
# Copyright (C) 2022-2023 David C. Harrison. All right reserved.
#
# You may not use, distribute, publish, or modify this code without
# the express written permission of the copyright holder.
#
#######################################################################
*/

import SwiftUI

struct Assignment5View: View {
  var body: some View {
    VStack {
      Spacer()
      Label("CSE118 Assignment 5", systemImage: "person.3.sequence")
      Spacer()
      Spacer()
    }
    .padding()
  }
}

#if !TESTING
struct Assignment5View_Previews: PreviewProvider {
  static var previews: some View {
    Assignment5View()
  }
}
#endif
