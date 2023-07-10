import SwiftUI

struct Assignment4View: View {
    @State private var expression: String = ""
    @State private var result: String = ""
    
    var body: some View {
        VStack {
            Text("Postfix Calculator")
                .font(.title)
                .padding(.bottom, 20)

            TextField("Expression", text: $expression, onCommit: evaluateExpression)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal, 20)

            TextField("Result", text: $result)
                .disabled(true)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal, 20)

            HStack {
                Button(action: evaluateExpression) {
                    Text("Evaluate")
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                Button(action: clearFields) {
                    Text("Clear")
                        .padding()
                        .background(Color.red)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
            }
            .padding(.top, 20)

            Spacer()
        }
        .padding()
    }

    func evaluateExpression() {
        do {
            let calculator = PostfixCalculator()
            let evaluation = try calculator.parse(expression: expression)
            result = String(evaluation)
        } catch {
            result = "Error: \(error)"
        }
    }

    func clearFields() {
        expression = ""
        result = ""
    }
}

#if !TESTING
struct Assignment4View_Previews: PreviewProvider {
    static var previews: some View {
        Assignment4View()
    }
}
#endif

