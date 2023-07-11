import SwiftUI
import Combine

// Main View
struct Assignment4View: View {
    // Input string for postfix evaluation
    @State private var expression: String = ""
    // The result string of the postfix evaluation
    @State private var result: String = ""
    // The date to display in the calendar
    @State private var date: Date = Date()
    // Helper to generate calendar arrays
    private var calendarGenerator = CalendarGenerator()
    // Combine subscriptions container
    @State private var combineSubscriptions = Set<AnyCancellable>()

    // Date formatter to display date
    private var monthYearFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMMM yyyy"
        return formatter
    }()

    var body: some View {
        VStack {
            // Postfix Calculator UI
            HStack {
                Image(systemName: "plus.slash.minus") // Postfix Calculator icon
                Text("Postfix Calculator")
            }
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
            // Calendar Generator UI
            HStack {
                Image(systemName: "calendar") // Calendar Generator icon
                Text("Calendar Generator")
            }
            .font(.title)
            .padding(.top, 20)
            Text(monthYearFormatter.string(from: date))
                .font(.title2)
                .padding(.bottom, 20)
            ForEach(generateCalendarArray(), id: \.self) { week in
                HStack {
                    ForEach(week, id: \.self) { day in
                        Text(day > 0 ? "\(day)" : "")
                            .frame(width: 30, height: 30, alignment: .center)
                    }
                }
            }
            HStack {
                Button(action: previousMonth) {
                    Text("Previous")
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                Button(action: currentMonth) {
                    Text("Today")
                        .padding()
                        .background(Color.green)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                Button(action: nextMonth) {
                    Text("Next")
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
            }
            .padding(.top, 20)
            Spacer()
        }
        .padding()
        .keyboardAdaptive(cancellables: $combineSubscriptions)
    }
    
    // Function to evaluate postfix expression
    func evaluateExpression() {
        do {
            let calculator = PostfixCalculator()
            let evaluation = try calculator.parse(expression: expression)
            result = String(evaluation)
        } catch {
            result = "Error: \(error)"
        }
    }

    // Function to clear the input and result fields
    func clearFields() {
        expression = ""
        result = ""
    }

    // Function to generate a 2D array representing the current calendar month
    func generateCalendarArray() -> [[Int]] {
        let components = Calendar.current.dateComponents([.year, .month], from: date)
        return calendarGenerator.generate(yearAndMonth: components)
    }

    // Function to go to the previous month in the calendar
    func previousMonth() {
        date = Calendar.current.date(byAdding: .month, value: -1, to: date) ?? date
    }

    // Function to go to the next month in the calendar
    func nextMonth() {
        date = Calendar.current.date(byAdding: .month, value: 1, to: date) ?? date
    }

    // Function to go to the current month in the calendar
    func currentMonth() {
        date = Date()
    }
}

// ViewModifier to handle keyboard appearance and disappearance
struct KeyboardAdaptive: ViewModifier {
    @Binding var combineSubscriptions: Set<AnyCancellable>

    @State private var bottomPadding: CGFloat = 0

    func body(content: Content) -> some View {
        GeometryReader { geometry in
            content
                .padding(.bottom, self.bottomPadding)
                .onAppear {
                    self.addObserver()
                }
                .onDisappear {
                    self.removeObserver()
                }
        }
    }

    // Function to add observers for keyboard show/hide notifications
    private func addObserver() {
        NotificationCenter.default.publisher(for: UIResponder.keyboardWillShowNotification)
            .sink { keyboard in
                let keyboardScreenEndFrame = (keyboard.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue
                let keyboardViewEndFrame = UIApplication.shared.connectedScenes
                    .filter({$0.activationState == .foregroundActive})
                    .compactMap({$0 as? UIWindowScene})
                    .first?.windows
                    .filter({$0.isKeyWindow})
                    .first?.convert(keyboardScreenEndFrame ?? CGRect.zero, from: nil)

                bottomPadding = keyboardViewEndFrame?.height ?? 0
            }
            .store(in: &combineSubscriptions)

        NotificationCenter.default.publisher(for: UIResponder.keyboardWillHideNotification)
            .sink { _ in
                bottomPadding = 0
            }
            .store(in: &combineSubscriptions)
    }

    // Function to remove all observers
    private func removeObserver() {
        combineSubscriptions.removeAll()
    }
}

extension View {
    func keyboardAdaptive(cancellables: Binding<Set<AnyCancellable>>) -> some View {
        ModifiedContent(content: self, modifier: KeyboardAdaptive(combineSubscriptions: cancellables))
    }
}

#if !TESTING
struct Assignment4View_Previews: PreviewProvider {
    static var previews: some View {
        Assignment4View()
    }
}
#endif

