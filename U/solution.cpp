#include <iostream>
#include <iomanip>
#include <algorithm>
#include <cassert>

int main() {
  int tests; std::cin >> tests;
  std::cout << std::setprecision(17) << std::fixed;
  while (tests --> 0) {
    int d;
    double p;
    std::cin >> d >> p;
    std::vector<double> probability(d);
    double answer = 0.0;
    answer = std::max(answer, 1.0 / d);
    answer = std::max(answer, (1 - p) * (d - 1) / d / (d - 2) +
        p * (d - 1) * (d - 1) / d / d / (d - 2));
    answer = std::max(answer, (1 - p) * (d - 1) / d / (d - 2) +
        p * (d - 1) / d / d);
    answer = std::max(answer, (1 - p) * (d - 1) / d / (d - 2)); 
    assert(answer == (1 - p) * (d - 1) / d / (d - 2) + p * (d - 1) * (d - 1) / d / d / (d - 2));
    std::cout << answer << std::endl;
  }
  return 0;
}
